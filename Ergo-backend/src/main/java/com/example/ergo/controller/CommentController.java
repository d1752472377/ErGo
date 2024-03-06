package com.example.ergo.controller;



import com.example.ergo.config.Result;
import com.example.ergo.entity.Comment;
import com.example.ergo.service.CommentService;
import com.example.ergo.vo.dto.CommentDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2024-01-23 19:31:38
 */
@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController{

    @Autowired
    private CommentService commentService;
    /**
     * 查询
     */
    @Operation(summary = "查询评论")
    @GetMapping("/getList")
    public Result<Map<Object, Object>> getList(int articleId, int pageNum, int pageSize){
        List<CommentDto> commentList = commentService.getCommentList(articleId, pageNum, pageSize);
        int allComment = commentService.getAllComment (articleId);
        int total = commentService.getTotalPage(articleId,0);
        int totalPage = (total-1)/10 +1;
        Map<Object, Object> map = new HashMap<>();
        map.put("allComment",allComment);
        map.put("totalPage",totalPage);
        map.put("commentList",commentList);
        return Result.success(map);
    }


    /**
     * 添加评论
     */
    @Operation(summary = "添加评论")
    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment){
        int i = commentService.addComment(comment);
        if (i == 1){
            return Result.success();
        }
        return Result.fail("修改失败");
    }
    /**
     * 删除评论
     */
    @Operation(summary = "删除评论")
    @GetMapping("/deleteComment")
    public Result deleteComment(){
        return Result.success();
    }

    /*-------------------------后台------------------------------------*/
    @Operation(summary = "后台查询评论")
    @GetMapping("/getListByPage")
    public Result<Map<Object, Object>> getListByPage(int pagesize,int currentPage){
        Map map =commentService.getCOmmentListByPage(pagesize,currentPage);
        return Result.success(map);
    }
    @Operation(summary = "后台删除评论")
    @GetMapping("/deleteCommentById")
    public Result deleteCommentById(int id){
        commentService.removeById(id);
        return Result.success("请求成功");
    }

}

