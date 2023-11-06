package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.ergo.config.SnowFlakeUtil;
import com.example.ergo.entity.User;
import com.example.ergo.vo.dto.UserDTO;
import com.example.ergo.mapper.UserMapper;
import com.example.ergo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户登录表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-09-20 20:28:25
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public User passwordlogin(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getUserName, username);
        wrapper.eq(User::getPassword,password);
        User user= userMapper.selectOne(wrapper);
        if(user == null){
            return null;
        }


        return user;
    }
    @Override
    public UserDTO emaillogin(String email, String password) {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
//        wrapper.eq(User::getEmail, email);
//        wrapper.eq(User::getPassword,password);
//        User user= userMapper.selectOne(wrapper);
//        if(user == null){
//            return null;
//        }
        UserDTO userDTO = new UserDTO();
//        BeanUtils.copyProperties(user,UserDTO.class);
        return userDTO;
    }

    @Override
    public Integer register(UserDTO userDTO) {
        String userName = userDTO.getUserName();
        String password = userDTO.getPassword();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null){
            return 0;
        }
        User user1 = new User();
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(12, 13);
        Long id = snowFlakeUtil.getNextId();
        user1.setId(id);
        user1.setUserName(userName);
        user1.setPassword(password);
        userMapper.insert(user1);
        System.out.println("账号为："+userName);
        return 1;
    }

    @Override
    public Map getUserList(Integer pageSize, Integer pageNum) {
        return null;
    }


}

