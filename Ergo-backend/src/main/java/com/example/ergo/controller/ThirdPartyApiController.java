package com.example.ergo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ergo.config.Result;
import com.example.ergo.util.OkHttpClientUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author autor
 * @date 2024/3/8
 * 描述：
 */
@Tag(name = "第三方Api")
@RequestMapping("/third")
@CrossOrigin
@Slf4j
@RestController
public class ThirdPartyApiController {
    public String zhihuUrl ="https://gateway.moguit.cn/mogu-web/hotSearch/getOutsideHotSearch?type=zhihu";
    public String weiboUrl ="https://gateway.moguit.cn/mogu-web/hotSearch/getOutsideHotSearch?type=weibo";
    public String csdnUrl = "https://gateway.moguit.cn/mogu-web/hotSearch/getOutsideHotSearch?type=csdn";
    public String toutiaoUrl = "https://gateway.moguit.cn/mogu-web/hotSearch/getOutsideHotSearch?type=toutiao";
    public String baiduUrl ="https://gateway.moguit.cn/mogu-web/hotSearch/getOutsideHotSearch?type=baidu";
    public String biliUrl ="https://tenapi.cn/v2/bilihot";

    @Operation(summary = "获取知乎热榜")
    @GetMapping("/zhihu")
    public Object getZhiHu(){
        String s = OkHttpClientUtil.doGet(zhihuUrl);
        JSONObject jsonObject =  JSON.parseObject(s);
        return jsonObject;
    }
    @Operation(summary = "获取微博热榜")
    @GetMapping("/weibo")
    public Object getWeibo(){
        String s = OkHttpClientUtil.doGet(weiboUrl);
        JSONObject jsonObject = JSON.parseObject(s);
        return jsonObject;
    }
    @Operation(summary = "获取csdn热榜")
    @GetMapping("/csdn")
    public Object getCSDN(){
        String s = OkHttpClientUtil.doGet(csdnUrl);
        JSONObject jsonObject =  JSON.parseObject(s);
        return jsonObject;
    }
    @Operation(summary = "获取百度热榜")
    @GetMapping("/baidu")
    public Object getBaidu(){
        String s = OkHttpClientUtil.doGet(baiduUrl);
        JSONObject jsonObject =  JSON.parseObject(s);
        return jsonObject;
    }
    @Operation(summary = "获取B站热榜")
    @GetMapping("/bili")
    public Object getBili(){
        String s = OkHttpClientUtil.doGet(biliUrl);
        JSONObject jsonObject =  JSON.parseObject(s);
        return jsonObject;
    }
    @Operation(summary = "获取头条热榜")
    @GetMapping("/toutiao")
    public Object getToutiao(){
        String s = OkHttpClientUtil.doGet(toutiaoUrl);
        JSONObject jsonObject =  JSON.parseObject(s);
        return jsonObject;
    }
}
