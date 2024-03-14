package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.User;
import com.example.ergo.entity.UserInfo;
import com.example.ergo.mapper.UserInfoMapper;
import com.example.ergo.mapper.UserMapper;
import com.example.ergo.service.UserService;
import com.example.ergo.vo.dto.UserDTO;
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
    @Autowired
    private UserInfoMapper userInfoMapper;

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
        String userName = userDTO.getEmail();
        String password = userDTO.getPassword();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null){
            return 0;
        }
        User user1 = new User();
        user1.setUserName(userName);
        user1.setPassword(password);
        userMapper.insert(user1);
        User user2 = userMapper.selectOne(queryWrapper);
        Integer id = user2.getId();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setEmail(userName);
        userInfo.setUserName(userDTO.getUserName());
        userInfo.setPhoto(userDTO.getPhoto());
        userInfo.setUserRole(0);
        userInfoMapper.insert(userInfo);
        return 1;
    }

    @Override
    public Map getUserList(Integer pageSize, Integer pageNum) {
        return null;
    }


}

