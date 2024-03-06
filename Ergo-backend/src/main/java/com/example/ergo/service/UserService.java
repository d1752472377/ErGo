package com.example.ergo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ergo.entity.User;
import com.example.ergo.entity.UserInfo;
import com.example.ergo.vo.dto.UserDTO;

import java.util.Map;


/**
 * 用户登录表(User)表服务接口
 *
 * @author makejava
 * @since 2023-09-20 20:28:25
 */
public interface UserService extends IService<User> {

    User passwordlogin(String username, String password);

     Integer register(UserDTO userDTO);//成功 失败（已存在账号）0/1

    Map getUserList(Integer pageSize, Integer pageNum);

    UserDTO emaillogin(String email, String password);

}

