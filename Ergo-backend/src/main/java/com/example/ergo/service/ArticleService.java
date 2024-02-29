package com.example.ergo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ergo.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 文章表(Article)表服务接口
 *
 * @author makejava
 * @since 2023-09-27 00:23:05
 */
@Service
public interface ArticleService extends IService<Article> {

    Map getArticle(Integer pageNum , Integer pageSize);

    Integer write(Article article);

    Integer operate(Article article);

    Integer updateTop(Article article);

    Integer updateRecommend(Article article);

    Integer deleteArticleById(Integer id);

    List<Article> findByTitleLike(String keyword);
    List queryArticleBySearchKey(String key);

    Map getArticleInfo(Integer id);
}

