package com.example.ergo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Article;
import com.example.ergo.mapper.ArticleMapper;
import com.example.ergo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ergo.enums.PushStatusEnum.*;

/**
 * @author 董冠男
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
    // TODO 增删改查
    @Operation(summary = "保存文章")
    @PostMapping("save")
    public Result save(@RequestBody Article article){
        Integer status = article.getStatus();
        if (status != OFFLINE.getCode() && status != ONLINE.getCode() && status != REVIEW.getCode()){
            return Result.fail("文章状态不合法");
        }
        //获取json
        articleService.write(article);
        return null;
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
        if(i ==1){
            return Result.success(200,"成功");
        }
        return Result.fail("成功");
    }


    /**
     * 分页查询文章
     * @param PageSize
     * @param PageNum
     * @return
     */
    @Operation(summary = "分页查询文章")
    @GetMapping("/list")
    public Result list(
            @RequestParam(name = "PageSize") Integer PageSize,
            @RequestParam(name = "PageNum") Integer PageNum){
        Map map = articleService.getArticle(PageNum, PageSize);

        return Result.success("查询成功",map);
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
}
