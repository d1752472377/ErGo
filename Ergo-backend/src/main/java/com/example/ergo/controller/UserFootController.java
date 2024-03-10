package com.example.ergo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Article;
import com.example.ergo.entity.UserFoot;
import com.example.ergo.mapper.UserFootMapper;
import com.example.ergo.service.ArticleService;
import com.example.ergo.service.UserFootService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
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
    @Operation(summary = "查询文章阅读数量")
    @GetMapping("/getNumberOfReadForArticle")
    public Result getNumberOfReadForArticle(@RequestParam(name = "documentId")int documentId){

        LambdaQueryWrapper<UserFoot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserFoot::getDocumentId,documentId)
                .eq(UserFoot::getReadStat,1);
        Long count = userFootMapper.selectCount(queryWrapper);
        return Result.success(count);
    }


}
