package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.Comment;
import com.example.ergo.vo.dto.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-23 19:31:39
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentDto> getCommentList(int articleId, int pageNum, int pageSize);

    List<CommentDto> listTreeComment(Integer id);
    String getParentName(Integer id);
}
