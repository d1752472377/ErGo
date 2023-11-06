package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.User;
import com.example.ergo.entity.UserInfo;
import com.example.ergo.mapper.UserInfoMapper;
import com.example.ergo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户个人信息表(UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2023-10-16 21:08:13
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public Map<String,Object> getUserList(Integer pageSize,Integer pageNum) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.orderByAsc(UserInfo::getId);
        Page<UserInfo> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);

        IPage<UserInfo> page1 = userInfoMapper.selectPage(page,queryWrapper);
        List<UserInfo> users = page1.getRecords();
        Long total = page1.getTotal();

        Map map = new HashMap<>();
        map.put("list",users);
        map.put("total",total);
        return map;
    }
    @Override
    public UserInfo getById(Long id){
        Integer userId= Math.toIntExact(id);
        UserInfo userInfo = userInfoMapper.getByUserId(userId);
        System.out.println(userInfo);
        return userInfo;
    }
}

