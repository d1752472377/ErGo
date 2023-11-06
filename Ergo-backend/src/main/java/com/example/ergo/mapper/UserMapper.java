package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 用户登录表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-09-20 20:28:25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> getUserList(Integer pageSize,Integer pageNum);
}
