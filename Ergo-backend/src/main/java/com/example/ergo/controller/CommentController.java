package com.example.ergo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ergo.config.Result;
import com.example.ergo.entity.Comment;
import com.example.ergo.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2024-01-23 19:31:38
 */
@RestController
@RequestMapping("comment")
public class CommentController{

    /**
     * 查询
     */
    @Operation(summary = "查询评论")
    @GetMapping("/getList")
    public Result getList(int articleId,int pageNum,int pageSize){
        //查询文章id下的评论 => 先查出顶级id（parent_comment_id = 0） => 再查询下面的回复( top_comment_id = comment_id)
        //查询顶级id

        return Result.success();
    }
    /**
     * 添加评论
     */
    @Operation(summary = "添加评论")
    @GetMapping("/addComment")
    public Result addComment(){
        return Result.success();
    }
    /**
     * 删除评论
     */
    @Operation(summary = "删除评论")
    @GetMapping("/deleteComment")
    public Result deleteComment(){
        return Result.success();
    }
}

