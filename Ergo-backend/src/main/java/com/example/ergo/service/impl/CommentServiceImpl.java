package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.Article;
import com.example.ergo.entity.Comment;
import com.example.ergo.entity.UserInfo;
import com.example.ergo.mapper.ArticleMapper;
import com.example.ergo.mapper.CommentMapper;
import com.example.ergo.mapper.UserInfoMapper;
import com.example.ergo.service.CommentService;
import com.example.ergo.vo.dto.CommentAndArticleAndUserInfoDTO;
import com.example.ergo.vo.dto.CommentDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

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
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<CommentDto> getCommentList(int articleId, int pageNum, int pageSize) {
        List<CommentDto> commentVos = commentMapper.getCommentList(articleId, pageNum, pageSize);
        List<CommentDto> sons = getSons(commentVos);
        getParent(sons);
        return sons;
    }

    @Override
    public int getAllComment(int articleId) {
        LambdaQueryWrapper<Comment> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        int count = Math.toIntExact(commentMapper.selectCount(queryWrapper));
        return count;
    }

    @Override
    public int getTotalPage(int articleId, int parentCommentId) {
        LambdaQueryWrapper<Comment> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getParentCommentId,parentCommentId);
        int count = Math.toIntExact(commentMapper.selectCount(queryWrapper));
        return count;
    }

    @Override
    public int addComment(Comment comment) {
        int i =commentMapper.saveComment(comment);
        return i;
    }

    @Override
    public Map getCOmmentListByPage(int pagesize, int currentPage) {
        Page<Comment> commentPage = new Page<>(currentPage , pagesize);
        IPage<Comment> commentIPage = commentMapper.selectPage(commentPage,null);
        List<Object> list =new ArrayList<>();
        for (Comment comment: commentPage.getRecords()) {
            UserInfo userInfo = userInfoMapper.getByUserId(comment.getUserId());
            Article article = articleMapper.selectById(comment.getArticleId());
            CommentAndArticleAndUserInfoDTO cau = new CommentAndArticleAndUserInfoDTO();
            BeanUtils.copyProperties(comment,cau);
            cau.setTitle(article.getTitle());
            cau.setPhoto(userInfo.getPhoto());
            cau.setUserName(userInfo.getUserName());
            list.add(cau);
        }

        Map<String,Object> map =new HashMap<>();
        map.put("list",list);
        map.put("Total",commentIPage.getTotal());
        return map;
    }

    private String getParentName(Integer id) {
        return commentMapper.getParentName(id);
    }

    private List<CommentDto> getSons(Integer parentId) {
        return commentMapper.listTreeComment(parentId);
    }

    private List<CommentDto> getSons(List<CommentDto> parents) {
        if (parents == null || parents.isEmpty()) {
            return null;
        }
        for (CommentDto parent : parents) {
            int parentId = parent.getId();
            int getParentId = parent.getParentCommentId();
            List<CommentDto> sonCommentVos = getSons(parentId);
            parent.setReplyComments(getSons(sonCommentVos));
            parent.setParentName(getParentName(getParentId));
        }
        return parents;
    }

    private void getParent(List<CommentDto> rawComments) {
        if (rawComments == null){
            return ;
        }
        for (CommentDto topComment : rawComments) {
            LinkedList<CommentDto> comments = new LinkedList<>();
            List<CommentDto> replyCmtsByTopCmt = topComment.getReplyComments();
            if (replyCmtsByTopCmt != null) {
                for (CommentDto replyComment : replyCmtsByTopCmt) {
                    handleChild(replyComment, comments, topComment.getUserName());
                }
            }
            topComment.setReplyComments(comments);
        }
    }

    private void handleChild(CommentDto replyComment, List<CommentDto> parent, String parentName) {
        List<CommentDto> grandchildren = replyComment.getReplyComments();
        replyComment.setReplyComments(null);
        parent.add(replyComment);
        if (grandchildren != null) {
            for (CommentDto grandChild : grandchildren) {
                grandChild.setParentName(replyComment.getUserName());
                if (grandChild != null) {
                    handleChild(grandChild, parent, parentName);
                }
            }
        }
    }
}
