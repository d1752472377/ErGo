package com.example.ergo.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户足迹表(UserFoot)表实体类
 *
 * @author makejava
 * @since 2024-02-24 03:09:47
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_foot")
public class UserFoot  {
    //主键ID@TableId
    private Integer id;

    //用户ID
    private Integer userId;
    //文档ID（文章/评论）
    private Integer documentId;
    //文档类型：1-文章，2-评论
    private Integer documentType;
    //发布该文档的用户ID
    private Integer documentUserId;
    //收藏状态: 0-未收藏，1-已收藏，2-取消收藏
    private Integer collectionStat;
    //阅读状态: 0-未读，1-已读
    private Integer readStat;
    //评论状态: 0-未评论，1-已评论，2-删除评论
    private Integer commentStat;
    //点赞状态: 0-未点赞，1-已点赞，2-取消点赞
    private Integer praiseStat;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
