package com.example.ergo.config;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 董冠男
 * @date 2023/11/25
 * 描述：
 */
@RestController
@RequestMapping("/es1")
class ElasticsearchHandleTest {

    @Autowired
    public ElasticsearchHandle elasticsearchHandle;

//    @Operation(summary = "创建索引")
//    @GetMapping("/index")
//    public Result createIndex(@RequestParam(value = "indexName") String indexName){
//        System.out.println(666);
//        elasticsearchHandle.createIndex(indexName);
//        return Result.success("成功");
//    }
}