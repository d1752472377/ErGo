package com.example.ergo.util;

import com.example.ergo.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author autor
 * @date 2024/3/12
 * 描述：
 */
//指定为切面类
@Aspect
//将该类放入Spring容器中
@Component
@Slf4j
public class MyAspect {

    @Autowired
    @Lazy
    public ArticleService articleService;
    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("execution(* com.example.ergo.controller.ArticleController.getArticleInfo(..))")
    public void myPointcut(){}

    @After("execution(* com.example.ergo.controller.ArticleController.getArticleInfo(..))")
    public void doAfter( JoinPoint joinPoint) throws Throwable {
        Object[] objs=joinPoint.getArgs();

        int id= (int) objs[0];
        log.info("进入切面");
        //根据id更新浏览量
        redisUtil.incrementHashValue("readCount", String.valueOf(id),1);

    }
}
