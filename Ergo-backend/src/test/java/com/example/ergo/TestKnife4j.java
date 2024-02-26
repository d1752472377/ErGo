package com.example.ergo;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @date 2023/9/18
 * 描述：测试knife4j
 */

public class TestKnife4j {
    @ApiOperation("测试")
    @RequestMapping(value = "testknife4j",method = RequestMethod.POST)
    public String testknife4j(){

        return "我看看怎么事！";
    }
}
