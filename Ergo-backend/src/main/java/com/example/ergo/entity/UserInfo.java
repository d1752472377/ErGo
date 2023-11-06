package com.example.ergo.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户个人信息表(UserInfo)表实体类
 *
 * @author makejava
 * @since 2022-10-16 21:03:41
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo  {
    //主键ID@TableId
    private Integer id;

    //用户ID
    private Integer userId;
    //用户名
    private String userName;
    //手机号;
    private String phone;
    //用户图像
    private String photo;
    //职位
    private String position;
    //公司
    private String company;
    //个人简介
    private String profile;
    //0 普通用户 1 超管
    private Integer userRole;
    //扩展字段
    private String extend;
    //用户的ip信息
    private Object ip;
    //是否删除
    private Boolean status;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
