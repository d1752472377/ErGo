package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.Comment;
import com.example.ergo.mapper.CommentMapper;
import com.example.ergo.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2024-01-23 19:31:40
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}

