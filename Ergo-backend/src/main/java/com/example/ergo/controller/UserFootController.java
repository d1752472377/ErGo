package com.example.ergo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Article;
import com.example.ergo.entity.UserFoot;
import com.example.ergo.mapper.UserFootMapper;
import com.example.ergo.service.ArticleService;
import com.example.ergo.service.UserFootService;
import com.example.ergo.util.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2024/2/24
 * 描述：
 */
@RestController
@CrossOrigin
@Log4j2
@RequestMapping("foot")
public class UserFootController {
    @Autowired
    private UserFootService userFootService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserFootMapper userFootMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Operation(summary = "浏览历史")
    @GetMapping("/getUserFootForRead")
    public Result getUserFootForRead(@RequestParam(name = "userId") int userId){
        LambdaQueryWrapper<UserFoot> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFoot::getUserId,userId)
                        .eq(UserFoot::getReadStat,1);
        List<UserFoot> list = userFootService.list(queryWrapper);
        List<Article> collectedArticle = new ArrayList<>();
        for (UserFoot foot:list){
            Integer documentId = foot.getDocumentId();
            //暂时先随便查一查
            LambdaQueryWrapper<Article> wrapper =new LambdaQueryWrapper<>();
            wrapper.eq(Article::getId,documentId);
            List<Article> list1 = articleService.list(wrapper);
            collectedArticle.addAll(list1);
        }
        return Result.success(collectedArticle);
    }

    @Operation(summary = "收藏记录")
    @GetMapping("/collection")
    public Result getUserFootForCollection(@RequestParam(name = "userId") int userId){
        LambdaQueryWrapper<UserFoot> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFoot::getUserId,userId)
                .eq(UserFoot::getCollectionStat,1);
        Long count = userFootMapper.selectCount(queryWrapper);
        List<UserFoot> list = userFootService.list(queryWrapper);
        List<Article> collectedArticle = new ArrayList<>();
        for (UserFoot foot:list){
            Integer documentId = foot.getDocumentId();
            //暂时先随便查一查
            LambdaQueryWrapper<Article> wrapper =new LambdaQueryWrapper<>();
            wrapper.eq(Article::getId,documentId);
            List<Article> list1 = articleService.list(wrapper);
            collectedArticle.addAll(list1);
        }
        Map<String ,Object> map = new HashMap<>();
        map.put("list",collectedArticle);
        map.put("count",count);
        return Result.success(map);
    }

    @Operation(summary = "查询某用户所写文章被点赞数量")
    @GetMapping("/getNumberOfLikesOfArticlesWrittenByUser")
    public Result getNumberOfLikesOfArticlesWrittenByUser(@RequestParam(name = "userId")int userId){

        LambdaQueryWrapper<UserFoot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFoot::getDocumentUserId,userId)
                .eq(UserFoot::getPraiseStat,1);
        Long count = userFootMapper.selectCount(queryWrapper);
        return Result.success(count);
    }
    @Operation(summary = "查询某用户所写文章被收藏数量")
    @GetMapping("/getNumberOfCollectOfArticlesWrittenByUser")
    public Result getNumberOfCollectOfArticlesWrittenByUser(@RequestParam(name = "userId")int userId){

        LambdaQueryWrapper<UserFoot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFoot::getDocumentUserId,userId)
                .eq(UserFoot::getCollectionStat,1);
        Long count = userFootMapper.selectCount(queryWrapper);
        return Result.success(count);
    }
    @Operation(summary = "查询文章|点赞|收藏阅读数量")
    @GetMapping("/getNumberOfReadForArticle")
    public Result getNumberOfReadForArticle(@RequestParam(name = "documentId")int documentId){

//        LambdaQueryWrapper<UserFoot> queryWrapperForRead = new LambdaQueryWrapper<>();
//        queryWrapperForRead.eq(UserFoot::getDocumentId,documentId)
//                .eq(UserFoot::getReadStat,1);
//        Long countForRead = userFootMapper.selectCount(queryWrapperForRead);
        Map<String, Integer> readCount = redisUtil.getAllValuesByKey("readCount");
        int countForRead = 0;
        for (Map.Entry<String, Integer> entry : readCount.entrySet()){
            int articleId = Integer.parseInt(entry.getKey());
            Integer cnt = entry.getValue();
            if(articleId == documentId){
                countForRead = cnt;
            }
        }
        LambdaQueryWrapper<UserFoot> queryWrapperForPraise = new LambdaQueryWrapper<>();
        queryWrapperForPraise.eq(UserFoot::getDocumentId,documentId)
                .eq(UserFoot::getReadStat,1);
        Long countForPraise = userFootMapper.selectCount(queryWrapperForPraise);
        LambdaQueryWrapper<UserFoot> queryWrapperForCollection = new LambdaQueryWrapper<>();
        queryWrapperForCollection.eq(UserFoot::getDocumentId,documentId)
                .eq(UserFoot::getReadStat,1);
        Long countForCollection = userFootMapper.selectCount(queryWrapperForCollection);
        Map<String,Object> map = new HashMap<>();
        map.put("countForRead",countForRead);
        map.put("countForPraise",countForPraise);
        map.put("countForCollection",countForCollection);
        return Result.success(map);
    }
    @Operation(summary = "查询/添加阅读状态")
    @GetMapping("/queryOrAddReadStatus")
    public Result queryOrAddReadStatus(@RequestParam(name = "userId") int userId,
                                       @RequestParam(name = "articleId") int articleId,
                                       @RequestParam(name = "documentUserId") int documentUserId){
        LambdaQueryWrapper<UserFoot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFoot::getUserId,userId)
                .eq(UserFoot::getDocumentUserId,documentUserId)
                .eq(UserFoot::getDocumentId,articleId);
        UserFoot one = userFootService.getOne(queryWrapper);
        if (one == null){
            userFootService.addReadStatus(userId,articleId,documentUserId);
            return Result.success();
        }else {
            if (one.getReadStat() == 0){
                LambdaUpdateWrapper<UserFoot> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(UserFoot::getUserId,userId)
                        .eq(UserFoot::getDocumentId,articleId)
                        .eq(UserFoot::getDocumentUserId,documentUserId)
                        .set(UserFoot::getReadStat,1);
                userFootService.update(updateWrapper);
                return Result.success();
            }else {
                return Result.success();
            }
        }
    }

    @Operation(summary = "添加收藏状态/取消收藏")
    @GetMapping("/addCollectionStatus")
    public Result addCollectionStatus(@RequestParam(name = "userId") int userId,
                                      @RequestParam(name = "articleId") int articleId,
                                      @RequestParam(name = "documentUserId") int documentUserId,
                                      @RequestParam(name = "collectionState") int collectionState){
        LambdaUpdateWrapper<UserFoot> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserFoot::getUserId,userId)
                .eq(UserFoot::getDocumentUserId,documentUserId)
                .eq(UserFoot::getDocumentId,articleId)
                .set(UserFoot::getCollectionStat,collectionState);
        userFootService.update(updateWrapper);
        return Result.success();
    }
    @Operation(summary = "添加点赞状态/取消点赞")
    @GetMapping("/addPraiseStatus")
    public Result addPraiseStatus(@RequestParam(name = "userId") int userId,
                                  @RequestParam(name = "articleId") int articleId,
                                  @RequestParam(name = "documentUserId") int documentUserId,
                                  @RequestParam(name = "praiseState") int praiseState){
        LambdaUpdateWrapper<UserFoot> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserFoot::getUserId,userId)
                .eq(UserFoot::getDocumentUserId,documentUserId)
                .eq(UserFoot::getDocumentId,articleId)
                .set(UserFoot::getPraiseStat,praiseState);
        userFootService.update(updateWrapper);
        return Result.success();
    }
    @Operation(summary = "获取点赞状态")
    @GetMapping("/getPraiseStatus")
    public Result getPraiseStatus(@RequestParam(name = "userId") int userId,
                                  @RequestParam(name = "documentUserId") int documentUserId,
                                  @RequestParam(name = "articleId") int articleId){
        LambdaQueryWrapper<UserFoot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFoot::getUserId,userId)
                .eq(UserFoot::getDocumentId,articleId)
                .eq(UserFoot::getDocumentUserId,documentUserId);
        UserFoot one = userFootService.getOne(queryWrapper);
        if (one.getPraiseStat() == 0){
            return Result.success(false);
        }else if (one.getPraiseStat() == 2){
            return Result.success(false);

        }else if (one.getPraiseStat() == 1){
            return Result.success(true);
        }else {
            return Result.success(false);
        }
    }
    @Operation(summary = "获取收藏状态")
    @GetMapping("/getCollectionStatus")
    public Result getCollectionStatus(@RequestParam(name = "userId") int userId,
                                      @RequestParam(name = "documentUserId") int documentUserId,
                                      @RequestParam(name = "articleId") int articleId){
        LambdaQueryWrapper<UserFoot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFoot::getUserId,userId)
                .eq(UserFoot::getDocumentId,articleId)
                .eq(UserFoot::getDocumentUserId,documentUserId);

        UserFoot one = userFootService.getOne(queryWrapper);
        if (one.getCollectionStat() == 0){
            return Result.success(false);
        }else if (one.getCollectionStat() == 2){
            return Result.success(false);

        }else if (one.getCollectionStat() == 1){
            return Result.success(true);
        }else {
            return Result.success(false);
        }
    }

}
