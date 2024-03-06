package com.example.ergo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ergo.entity.Comment;
import com.example.ergo.vo.dto.CommentDto;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2024-01-23 19:31:40
 */
public interface CommentService extends IService<Comment> {

    List<CommentDto> getCommentList(int articleId, int pageNum, int pageSize);

    int getAllComment(int articleId);

    int getTotalPage(int articleId, int parentCommentId);

    int addComment(Comment comment);

    Map getCOmmentListByPage(int pagesize, int currentPage);
}

