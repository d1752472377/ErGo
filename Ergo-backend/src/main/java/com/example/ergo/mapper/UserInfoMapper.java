package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户个人信息表(UserInfo)表数据库访问层
 * @author 17524
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    UserInfo getByUserId(Integer id);

}
