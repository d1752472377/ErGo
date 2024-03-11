package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.UserFoot;
import com.example.ergo.mapper.UserFootMapper;
import com.example.ergo.service.UserFootService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户足迹表(UserFoot)表服务实现类
 *
 * @author makejava
 * @since 2024-02-24 03:09:47
 */
@Service("userFootService")
public class UserFootServiceImpl extends ServiceImpl<UserFootMapper, UserFoot> implements UserFootService {

    @Autowired
    private UserFootMapper userFootMapper;
    @Override
    public void addReadStatus(int userId, int articleId,int documentUserId) {
        UserFoot userFoot = new UserFoot();
        userFoot.setUserId(userId);
        userFoot.setDocumentUserId(documentUserId);
        userFoot.setDocumentId(articleId);
        userFoot.setReadStat(1);
        userFootMapper.insert(userFoot);

    }



}

