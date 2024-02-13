package com.example.ergo.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Tag;
import com.example.ergo.service.TagService;
import com.example.ergo.service.impl.TagServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 标签管理表(Tag)表控制层
 *
 * @author makejava
 * @since 2023-12-31 22:07:34
 */
@RestController
@RequestMapping("tag")
@CrossOrigin
public class TagController {

    @Autowired
    private TagService tagService;
    @GetMapping("index/getTagList")
    @Operation(summary = "获取标签列表")
    public Result getTagList(){
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Tag::getId).last("limit 25");
        List<Tag> list = tagService.list(queryWrapper);
        return Result.success(list);
    }
}
