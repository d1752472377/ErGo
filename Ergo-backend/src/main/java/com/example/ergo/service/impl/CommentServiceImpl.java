package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.Comment;
import com.example.ergo.mapper.CommentMapper;
import com.example.ergo.service.CommentService;
import com.example.ergo.vo.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2024-01-23 19:31:40
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<CommentDto> getCommentList(int articleId, int pageNum, int pageSize) {
        //查询文章评论
        List<CommentDto> commentList = commentMapper.getCommentList(articleId, pageNum, pageSize);
        //查询子评论
        for (CommentDto commentDto:commentList) {
            commentDto.setReplyComments(commentMapper.listTreeComment(commentDto.getId()));
        }
        return commentList;
    }
}

