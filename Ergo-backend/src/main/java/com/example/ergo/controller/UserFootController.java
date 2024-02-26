package com.example.ergo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Article;
import com.example.ergo.entity.UserFoot;
import com.example.ergo.service.ArticleService;
import com.example.ergo.service.UserFootService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 董冠男
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
}