package com.example.ergo.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
import java.util.Map;

/**
 * 标签管理表(Tag)表控制层
 *
 * @author makejava
 * @since 2023-12-31 22:07:34
 */
@RestController
@RequestMapping("/tag")
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
        System.out.println(list);
        return Result.success(list);
    }
    @GetMapping("/getAllTagList")
    @Operation(summary = "获取标签列表")
    public Result getAllTagList(){
        List<Tag> list = tagService.list();
        return Result.success(list);
    }

    @GetMapping("/getTagListByPage")
    @Operation(summary = "后台用的分页查询标签")
    public Result getTagListByPage(int pagesize,int currentPage){
        Map<String,Object> map = tagService.getListByPage(pagesize,currentPage);
        return Result.success(map);
    }
    @PutMapping("/updateTagByAdmin")
    @Operation(summary = "修改标签名字（后台）")
    public Result updateTagByAdmin(@RequestBody Tag tag){
        boolean b = tagService.updateById(tag);
        if (b == true){
            return Result.success("修改成功");
        }
        return Result.fail("修改失败");
    }
    @Operation(summary = "删除标签（后台）")
    @DeleteMapping("/deleteTagByAdmin")
    public Result deleteTagByAdmin(int id){
        boolean b = tagService.removeById(id);
        if (b == true){
            return Result.success("删除成功");
        }
        return Result.fail("修改失败");
    }
    @Operation(summary = "添加标签")
    @PostMapping("/addTagByAdmin")
    public Result addTagByAdmin(@RequestBody Tag tag){
        boolean save = tagService.save(tag);
        if (save == true){
            return Result.success("添加成功");
        }
        return Result.fail("添加失败");
    }
}
