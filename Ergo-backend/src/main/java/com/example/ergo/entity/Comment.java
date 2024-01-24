package com.example.ergo.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2024-01-23 19:31:39
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
public class Comment  {
    //主键ID@TableId
    private Integer id;

    //文章ID
    private Integer articleId;
    //用户ID
    private Integer userId;
    //评论内容
    private String content;
    //顶级评论ID
    private Integer topCommentId;
    //父评论ID
    private Integer parentCommentId;
    //是否删除
    private Integer deleted;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
