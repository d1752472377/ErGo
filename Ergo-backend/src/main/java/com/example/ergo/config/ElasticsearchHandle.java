package com.example.ergo.config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.FieldValue;
//import co.elastic.clients.elasticsearch._types.mapping.Property;
//import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
//import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
//import co.elastic.clients.elasticsearch.core.DeleteResponse;
//import co.elastic.clients.elasticsearch.core.GetResponse;
//import co.elastic.clients.elasticsearch.core.IndexResponse;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import co.elastic.clients.elasticsearch.core.search.Hit;
//import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
//import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
//import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.example.ergo.entity.Article;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 董冠男
 * @date 2023/11/24
 * 描述：
 */
@Slf4j
@Component
public class ElasticsearchHandle {
//    @Autowired
//    private ElasticsearchClient client;
//
//    /**
//     * 判断索引是否存在
//     *
//     * @param indexName
//     * @return
//     * @throws IOException
//     */
//    public boolean hasIndex(String indexName) throws IOException{
//        BooleanResponse exists = client.indices().exists(d->d.index(indexName));
//        return exists.value();
//    }
//
//    /**
//     * 删除索引
//     * @param indexName
//     * @return
//     * @throws IOException
//     */
//    public boolean deleteIndex(String indexName) throws IOException{
//        DeleteIndexResponse response = client.indices().delete(d -> d.index(indexName));
//        return true;
//    }
//
//    /**
//     * 创建索引
//     * @param indexName
//     * @return
//     */
//    public boolean createIndex(String indexName) {
//        try {
//            CreateIndexResponse indexResponse = client.indices().create(c ->c.index(indexName));
//        } catch (IOException e) {
//            log.error("索引创建失败：{}", e.getMessage());
//            throw new RuntimeException("创建索引失败");
//        }
//        return true;
//    }
//    /**
//     * 创建索引，不允许外部直接调用
//     *
//     * @param indexName
//     * @param mapping
//     * @throws IOException
//     */
//    private boolean createIndex(String indexName, Map<String, Property> mapping) throws IOException {
//        CreateIndexResponse createIndexResponse = client.indices().create(c -> {
//            c.index(indexName).mappings(mappings -> mappings.properties(mapping));
//            return c;
//        });
//        return createIndexResponse.acknowledged();
//    }
//
//    /**
//     * 重新创建索引,如果已存在先删除
//     *
//     * @param indexName
//     * @param mapping
//     */
//    public void reCreateIndex(String indexName, Map<String, Property> mapping) {
//        try {
//            if (this.hasIndex(indexName)) {
//                this.deleteIndex(indexName);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("删除索引失败");
//        }
//
//        try {
//            this.createIndex(indexName, mapping);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("重新创建索引失败");
//        }
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param indexName
//     * @throws IOException
//     */
//    public boolean insertDocument(String indexName, Object obj, String id){
//        try {
//            IndexResponse indexResponse = client.index(i -> i
//                    .index(indexName)
//                    .id(id)
//                    .document(obj));
//            return true;
//        } catch (IOException e) {
//            log.error("数据插入ES异常：{}", e.getMessage());
//            throw new RuntimeException( "ES新增数据失败");
//        }
//    }
//    /**
//     * 查询数据
//     *
//     * @param indexName
//     * @param id
//     * @return
//     */
//    public GetResponse<Article> searchDocument(String indexName, String id) {
//
//        try {
//            GetResponse<Article> getResponse = client.get(g -> g
//                            .index(indexName)
//                            .id(id)
//                    , Article.class
//            );
//            return getResponse;
//        } catch (IOException e) {
//            log.error("查询ES异常：{}", e.getMessage());
//            throw new RuntimeException( "查询ES数据失败");
//        }
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param indexName
//     * @param id
//     * @return
//     */
//    public boolean deleteDocument(String indexName, String id) {
//        try {
//            DeleteResponse deleteResponse = client.delete(d -> d
//                    .index(indexName)
//                    .id(id)
//            );
//        } catch (IOException e) {
//            log.error("删除Es数据异常：{}", e.getMessage());
//            throw new RuntimeException("数据删除失败");
//        }
//        return true;
//    }
//
///*------------------------------------------完善查询的一些方法----------------------------------------------------*/
//    /**
//     * 查询满足条件的数据
//     * @param indexName 索引名称
//     * @param query 查询的内容
//     * @param top 查询条数
//     * @return
//     */
//    public List<Article> search(String indexName, String query, int top) {
//        List<Article> documentParagraphs = Lists.newArrayList();
//        try {
//            SearchResponse<Article> search = client.search(s -> s
//                            .index(indexName)
//                            .query(q -> q
//                                    .match(t -> t
//                                            .field("content")
//                                            .query(query)
//                                    ))
//                            .from(0)
//                            .size(top)
////                    .sort(f -> f.field(o -> o.field("docId").order(SortOrder.Desc)))
//                    , Article.class
//            );
//            for (Hit<Article> hit : search.hits().hits()) {
//                Article pd = hit.source();
//                documentParagraphs.add(pd);
//            }
//        } catch (IOException e) {
//            log.error("查询ES异常：{}", e.getMessage());
//            throw new RuntimeException( "查询ES数据失败");
//        }
//        return documentParagraphs;
//    }
//

}
