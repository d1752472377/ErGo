package com.example.ergo.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户关系表(UserRelation)表实体类
 *
 * @since 2024-02-23 19:08:28
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_relation")
public class UserRelation  {
    //主键ID@TableId
    private Integer id;

    //用户ID
    private Integer userId;
    //关注userId的用户id，即粉丝userId
    private Integer followUserId;
    //阅读状态: 0-未关注，1-已关注，2-取消关注
    private Integer followState;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
