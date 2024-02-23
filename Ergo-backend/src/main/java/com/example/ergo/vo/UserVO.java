package com.example.ergo.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 董冠男
 * @date 2023/10/16
 * 描述：
 */
@Data
public class UserVO {
    private Integer id;
    //用户名
    private String userName;
    //邮箱;
    private String email;
    //手机号;
    private String phone;
    //是否删除
    private Boolean status;
    //头像
    private String photo;
    //权限
    private Integer userRole;
    private String profile;
    private Object ip;
    private Date createTime;


}
