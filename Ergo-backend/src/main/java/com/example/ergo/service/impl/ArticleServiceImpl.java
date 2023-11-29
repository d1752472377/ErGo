package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Article;
import com.example.ergo.vo.articleVO;
import com.example.ergo.mapper.ArticleMapper;
import com.example.ergo.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-09-27 00:23:05
 */
@Service("articleService")
@Slf4j
@Log4j2
@CrossOrigin
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private static final Logger LOGGER = LogManager.getLogger(ArticleServiceImpl.class);
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Override
    public Map getArticle(Integer pageNum, Integer pageSize ) {
        List<articleVO> article = articleMapper.getArticle(pageNum, pageSize);
        Integer total = articleMapper.getTotal();
        LOGGER.info("看看这是啥"+article);
        System.out.println(article);
        Map map = new HashMap<>();
        map.put("total",total);
        map.put("article",article);

        return map;
    }

    @Override
    public Integer write(Article article) {

        return null;
    }

    @Override
    public Integer operate(Article article) {

        int i = articleMapper.updateById(article);
        if (i == 1){
            return 1;
        }return 0;
    }

    @Override
    public Integer updateTop(Article article) {
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Article::getId,article.getId())
                .set(Article::getToppingStat,article.getToppingStat());
        int update = articleMapper.update(null, lambdaUpdateWrapper);
        if (update == 1){
            return 1;
        }return 0;
    }

    @Override
    public Integer updateRecommend(Article article) {
        LambdaUpdateWrapper<Article> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Article::getId,article.getId())
                .set(Article::getCreamStat,article.getCreamStat());
        int update = articleMapper.update(null, lambdaUpdateWrapper);
        if (update == 1){
            return 1;
        }return 0;
    }

    @Override
    public Integer deleteArticleById(Integer id) {

        return articleMapper.deleteArticleById(id);
    }

    /**
     * 搜索标题
     * @param keyword
     * @return
     */
    @Override
    public List<Article> findByTitleLike(String keyword) {
        return null;
    }



    /*---------------------------------ES全局搜索----------------------------------------------*/

    @Override
    public List<Article> queryArticleBySearchKey(String key){

        if (key == null){

        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(key, "title", "short_title");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        SearchRequest searchRequest = new SearchRequest(new String[]{"article"},searchSourceBuilder);
        SearchResponse searchResponse = null;
        System.out.println(searchRequest);
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits hits = searchResponse.getHits();
       
        SearchHit[] hitsList = hits.getHits();
        List<Integer> ids = new ArrayList<>();
        for (SearchHit documentFields : hitsList) {
            ids.add(Integer.parseInt(documentFields.getId()));
        }
        if (ObjectUtils.isEmpty(ids)) {
            return null;
        }
        List<Article> records = articleMapper.selectBatchIds(ids);
        return records;
    }
}

