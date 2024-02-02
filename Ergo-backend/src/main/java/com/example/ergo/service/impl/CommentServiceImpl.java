package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.Comment;
import com.example.ergo.mapper.CommentMapper;
import com.example.ergo.service.CommentService;
import com.example.ergo.vo.dto.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<CommentDto> commentVos = this.commentMapper.getCommentList(articleId,pageNum,pageSize);
        List<CommentDto> sons = getSons(commentVos);
        return sons;
    }
    private List<CommentDto> getSons(Integer parentId){
        return this.commentMapper.listTreeComment(parentId);
    }
    private List<CommentDto> getSons(List<CommentDto> parents){
        if(parents == null || parents.size() == 0){
            return null;
        }
        for (CommentDto parent : parents) {
            int parentId = parent.getId();
            List<CommentDto> sonCommentVos = getSons(parentId);
            //递归找子评论
            parent.setReplyComments(getSons(sonCommentVos));
        }
        return parents;
    }


}

