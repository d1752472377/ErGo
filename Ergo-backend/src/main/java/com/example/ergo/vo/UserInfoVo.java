package com.example.ergo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 董冠男
 * @date 2024/2/24
 * 描述：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {
    private Integer id;
    private Integer userId;
    private String userName;
    private String photo;
    //职位
    private String position;
    //公司
    private String company;
    //个人简介
    private String profile;
    private Object ip;
}
