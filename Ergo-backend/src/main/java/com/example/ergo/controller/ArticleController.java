package com.example.ergo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Article;
import com.example.ergo.entity.Category;
import com.example.ergo.entity.Comment;
import com.example.ergo.entity.UserInfo;
import com.example.ergo.enums.Constant;
import com.example.ergo.mapper.ArticleMapper;
import com.example.ergo.mapper.CommentMapper;
import com.example.ergo.service.ArticleService;
import com.example.ergo.service.CategoryService;
import com.example.ergo.service.CommentService;
import com.example.ergo.service.UserInfoService;
import com.example.ergo.util.AESUtil;
import com.example.ergo.vo.articleVO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ergo.enums.PushStatusEnum.*;

/**
 * @date 2023/9/27
 * 描述：
 */
@Tag(name = "文章管理")
@RestController()
@Slf4j
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;

    // TODO 增删改查
    @Operation(summary = "保存文章")
    @PostMapping("/save")
    public Result save(@RequestBody Article article){
        Integer status = article.getStatus();
        if (status != OFFLINE.getCode() && status != ONLINE.getCode() && status != REVIEW.getCode()){
            return Result.fail("文章状态不合法");
        }
        //获取json
        Integer write = articleService.write(article);
        return Result.success(write);
    }

    @Operation(summary = "操作文章")
    @PostMapping("/operate")
    public Result operate(@RequestBody Article article){
        Integer operate = articleService.operate(article);
        if (operate == 1){
            return Result.success(200,"修改成功");
        }
        return Result.fail("修改失败");
    }
    @Operation(summary = "修改置顶")
    @PutMapping("/updateTop")
    public Result updateTop(Article article){
        Integer integer = articleService.updateTop(article);
        if (integer == 1){
            return Result.success(200,"修改成功");
        }
        return Result.fail("修改失败");
    }
    @Operation(summary = "修改推荐")
    @PutMapping("/updateRecommend")
    public Result updateRecommend(Article article){
        Integer integer = articleService.updateRecommend(article);
        if (integer == 1){
            return Result.success(200,"修改成功");
        }
        return Result.fail("修改失败");
    }

    @Operation(summary = "删除文章")
    @DeleteMapping("/deleteArticleById")
    public Result deleteArticleById(Integer id){
        Integer i = articleService.deleteArticleById(id);
        //根据文章id查询评论
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId,id);
        commentService.remove(lambdaQueryWrapper);
        if(i ==1){
            return Result.success(200,"成功");
        }
        return Result.fail("成功");
    }
    @Operation(summary = "获取所有文章标题")
    @GetMapping("/getAllArticleTitle")
    public Result getAllArticleTitle(){
        List<Article> list = articleService.list();
        List idAndTitle= new ArrayList<>();
        for (Article article:list) {
            Map<String,Object> map =new HashMap<>();
            map.put("id",article.getId());
            map.put("title",article.getTitle());
            idAndTitle.add(map);
        }
        return Result.success(idAndTitle);
    }

    /**
     * 分页查询文章
     * @param pageSize
     * @param pageNo
     * @return
     */
    @Operation(summary = "分页查询文章")
    @GetMapping("/list")
    public Result list(
            @RequestParam(name = "pageSize") Integer pageSize,
            @RequestParam(name = "currentPage") Integer pageNo){
        Map map = articleService.getArticle(pageNo, pageSize);
        return Result.success(map);
    }
    @Operation(summary = "置顶头图")
    @GetMapping("/index/getBlogByLevel")
    public Result getBlogByLevel(@RequestParam(name = "level") int level ,
                                 @RequestParam(name = "useSort") int useSort){
        LambdaQueryWrapper<Article> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Article::getLevel,level)
                .gt(Article::getSort, useSort)
                .orderByDesc(Article::getCreateTime);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        Map map =new HashMap<>();
        map.put("list",articles);
        return Result.success(map);
    }
    @Operation(summary = "获取前台文章列表")
    @PostMapping("/web/list")
    // TODO 待优化
    public Result list(@RequestBody String requestData) throws Exception {
        String s = java.net.URLDecoder.decode(requestData, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(s);
        String str = jsonNode.get("requestData").textValue();
        String str1 = str.replaceAll(" +","+");
        System.out.println(str1);
        String keypair = redisTemplate.opsForValue().get(Constant.AES_KEY);
        System.out.println(keypair);
        String a = AESUtil.aesDecrypt(str1,keypair);
        System.out.println(a);
        JsonNode jsonNode1 = objectMapper.readTree(a);
        Integer pageNo = jsonNode1.get("pageNo").asInt();

        Integer pageSize = jsonNode1.get("pageSize").asInt();
        String sortField = jsonNode1.get("sortField").textValue();
        Map article = articleService.getArticle(pageNo, pageSize);
        return Result.success(article);
    }

    @Operation(summary = "根据id获取文章详情")
    @GetMapping("/getArticleInfo")
    public Result getArticleInfo(Integer id){
        Map article = articleService.getArticleInfo(id);
        return Result.success(article);
    }

    @Operation(summary = "获取用户所写文章")
    @GetMapping("/getUserWrite")
    public Result getUserWrite(@RequestParam(name = "userId") int userId){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getUserId,userId);
        List<Article> list = articleService.list(queryWrapper);
        Long count = articleMapper.selectCount(queryWrapper);
        Map<String,Object> map= new HashMap<>();
        map.put("list",list);
        map.put("count",count);
        return Result.success(map);
    }
    @Operation(summary = "根据标题获取文章")
    @GetMapping("/getArticleListByTitle")
    public Result getArticleListByTitle(String title,int pageSize,int currentPage){
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getTitle,title);
        Page<Article> page = new Page<>(currentPage,pageSize);
        IPage<Article> iPage = articleMapper.selectPage(page,queryWrapper);
        List<Article> list = iPage.getRecords();
        List<articleVO> articleVOList = new ArrayList<>();
        for (Article article:list){
            Category category = categoryService.getById(article.getCategoryId());
            UserInfo info = userInfoService.getById(article.getUserId());
            articleVO articleVO = new articleVO();
            BeanUtils.copyProperties(article, articleVO);
            if (category != null) {
                articleVO.setCategoryName(category.getCategoryName());
            }
            if (info != null) {
                articleVO.setUserName(info.getUserName());
            }
            articleVOList.add(articleVO);
        }
        Map<String,Object> map =new HashMap<>();
        map.put("list",articleVOList);
        map.put("total",iPage.getTotal());
        return Result.success(map);
    }

/*-------------------------------Elasticsearch 实验一下------------------------------------------------*/
    @GetMapping("/queryArticleSearchKey")
    public Result add(String key) {
        List<Article> articles = articleService.queryArticleBySearchKey(key);
        Map map = new HashMap<>();
        map.put("article",articles);
        return Result.success(map);
    }
}
