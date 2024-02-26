package com.example.ergo;

import com.example.ergo.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @date 2023/10/14
 * 描述：
 */
public class TEST {
    @Autowired
    private ArticleMapper articleMapper;
    public void test(){
        int pageSize = 10;
        int pageNum =1;
        List article = articleMapper.getArticle(pageNum, pageSize);
        System.out.println(article);
    }
}
