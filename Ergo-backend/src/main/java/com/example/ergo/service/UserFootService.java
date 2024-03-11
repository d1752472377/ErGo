package com.example.ergo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ergo.entity.UserFoot;


/**
 * 用户足迹表(UserFoot)表服务接口
 *
 * @author makejava
 * @since 2024-02-24 03:09:47
 */
public interface UserFootService extends IService<UserFoot> {
    //添加阅读状态（插入数据）
    void addReadStatus(int userId,int articleId,int documentUserId);

}

