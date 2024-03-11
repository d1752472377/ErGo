package com.example.ergo.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class TestAopAnnoAspect {

    @Pointcut("execution(* com.example.ergo.controller..*(..))")
    public void PointCut(){
    }

//    @Before("execution(* com.example.ergo.controller.ArticleController.getArticleInfo(..))")
//    public void Before() {
//        System.out.println("请求之前");
//    }

//    @Around("execution(* com.example.ergo.controller..*(..))")
//    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("环绕");
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        AnnotationTest annotationTest = method.getAnnotation(AnnotationTest.class);
//        String type = annotationTest.type();
//        System.out.println("type 为：" + type);
//        return joinPoint.proceed();
//    }
//
//    @After("execution(* com.example.demo.controller..*(..))")
//    public void After() {
//        System.out.println("在请求之后");
//    }
//
//    @AfterReturning("execution(* com.example.demo.controller..*(..))")
//    public void AfterReturning() {
//        System.out.println("在返回之后");
//    }
}
