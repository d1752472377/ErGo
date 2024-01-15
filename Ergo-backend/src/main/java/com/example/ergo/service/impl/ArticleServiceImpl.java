package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.Article;
import com.example.ergo.util.MarkdownToHtmlUtils;
import com.example.ergo.vo.articleInfoVo;
import com.example.ergo.vo.articleVO;
import com.example.ergo.mapper.ArticleMapper;
import com.example.ergo.service.ArticleService;
import com.example.ergo.vo.dto.ArticleDTO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private ArticleMapper articleMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Value("${elasticsearch.open}")
    private Boolean openES;
    @Override
    public Map<String,Object> getArticle(Integer pageNum, Integer pageSize ) {
        List<articleVO> article = articleMapper.getArticle(pageNum, pageSize);
        Integer total = articleMapper.getTotal();
//        LOGGER.info("看看这是啥"+article);
//        System.out.println(article);
        Map<String,Object> map = new HashMap<>(16);
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

    @Override
    public Map getArticleInfo(Integer id) {
        List<articleInfoVo> articleInfo = articleMapper.getArticleInfo(id);
        articleInfoVo info = articleInfo.get(0);
        String content = info.getContent();
        String htmlContent = MarkdownToHtmlUtils.MarkdownConverter.markdownToHtml(content);
        info.setContent(htmlContent);
//        System.out.println(htmlContent);
        Map<String,Object> map = new HashMap<>(16);
        map.put("info",articleInfo);
        return map;
    }
    /**
     * 搜索标题
     * @param keyword 关键字
     * @return 返回
     */
    @Override
    public List<Article> findByTitleLike(String keyword) {
        return null;
    }



    /*---------------------------------ES全局搜索----------------------------------------------*/

    @Override
    public List<Article> queryArticleBySearchKey(String key){
        System.out.println(key);
        // 如果不使用 Elasticsearch（openES 为 false），则从MySQL数据库中检索文章记录
        if ( !openES){
            LambdaQueryWrapper<Article> query =new LambdaQueryWrapper<>();
            // 添加条件：未删除的文章（deleted 字段为 0）、状态为在线的文章（status 字段为 1）
            query.eq(Article::getDeleted,0)
                    .eq(Article::getStatus,1)
                    // 添加动态条件：如果关键字不为空，检索标题或短标题包含关键字的文章
                    .and(!StringUtils.isEmpty(key),
                            v->v.like(Article::getTitle,key)
                                    .or()
                                    .like(Article::getShortTitle,key)
                                    .or()
                                    .like(Article::getSummary,key));
            // 指定查询的字段，只选择文章的 id、title 和 shortTitle 字段
            query.select(Article::getId,Article::getTitle,Article::getShortTitle,
                            Article::getContent)
                    // 在 SQL 查询语句的最后追加部分，这里是添加了 limit 10，表示只返回查询结果的前 10 条记录
                    .last("limit 10")
                    // 按照文章的 ID 字段降序排序查询结果
                    .orderByDesc(Article::getId);
            List<Article> articles = articleMapper.selectList(query);
            return articles;
        }

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建 Elasticsearch 的多字段匹配查询
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(key, "title", "short_title");
        searchSourceBuilder.query(multiMatchQueryBuilder);
        // 创建 Elasticsearch 的搜索请求对象，指定索引为 "article"
        SearchRequest searchRequest = new SearchRequest(new String[]{"article"},searchSourceBuilder);
        SearchResponse searchResponse = null;

        try {
            // 通过 restHighLevelClient 执行 Elasticsearch 搜索请求
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 从 Elasticsearch 搜索响应中获取命中的结果
//        SearchHits hits = searchResponse.getHits(); //SearchHit[] hitsList = hits.getHits();和下面是一样的，下边的解决了报空指针的问题
        SearchHits hits = null;
        if (searchResponse != null) {
            hits = searchResponse.getHits();
        }
        SearchHit[] hitsList = new SearchHit[0];
        if (hits != null) {
            hitsList = hits.getHits();
        }
        // 提取 Elasticsearch 搜索结果中的文章 ID，并将其转换为整数类型
        List<Integer> ids = new ArrayList<>();
        for (SearchHit documentFields : hitsList) {
            ids.add(Integer.parseInt(documentFields.getId()));
        }
        // 如果命中的结果为空，返回空列表
        if (ObjectUtils.isEmpty(ids)) {
            return null;
        }
        List<Article> articles = articleMapper.selectBatchIds(ids);

        return articles;
    }


}

