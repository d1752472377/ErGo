package com.example.ergo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ergo.entity.UserInfo;

import java.util.Map;


/**
 * 用户个人信息表(UserInfo)表服务接口
 *
 * @author makejava
 * @since 2023-10-16 21:08:11
 */
public interface UserInfoService extends IService<UserInfo> {

    Map getUserList(Integer pageSize,Integer pageNum);
    UserInfo getById(Long id);
}

