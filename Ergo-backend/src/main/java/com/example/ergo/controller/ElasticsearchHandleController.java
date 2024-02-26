package com.example.ergo.controller;

import com.example.ergo.config.ElasticsearchHandle;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Article;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @date 2023/11/25
 * 描述：
 */
@RestController()
@Slf4j
@CrossOrigin
@RequestMapping("/es")
public class ElasticsearchHandleController {
    @Autowired
    private ElasticsearchHandle elasticsearchHandle;
    @Autowired
    private RestHighLevelClient restHighLevelClient;



//    @Operation(summary = "创建索引")
//    @GetMapping("/index")
//    public Result createIndex(@RequestParam(value = "indexName") String indexName){
//        System.out.println(666);
//        elasticsearchHandle.createIndex(indexName);
//        return Result.success("成功");
//    }



}
