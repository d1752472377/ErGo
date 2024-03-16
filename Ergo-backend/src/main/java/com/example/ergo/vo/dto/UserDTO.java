package com.example.ergo.vo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2023/9/20
 * 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    //第三方用户ID
    private String thirdAccountId;
    //邮箱;
    private String email;
    private String code;
    //手机号;
    private String phone;
    //手机号;
    private String photo;
    //用户名
    private String userName;
    //密码
    private String password;
    //登录方式: 0-微信登录，1-账号密码登录
    private Integer loginType;


}
