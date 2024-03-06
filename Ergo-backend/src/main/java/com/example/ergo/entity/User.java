package com.example.ergo.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户登录表(User)表实体类
 *
 * @author makejava
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User  {
    //主键ID@TableId

    private Integer id;

    //第三方用户ID
    private String thirdAccountId;
    //邮箱;
    private String email;
    //手机号;
    private String phone;
    //用户名
    private String userName;
    //密码
    private String password;
    //登录方式: 0-微信登录，1-账号密码登录
    private Integer loginType;
    //是否删除
    private Integer deleted;
    //创建时间
    private Date createTime;
    //最后更新时间
    private Date updateTime;



}
