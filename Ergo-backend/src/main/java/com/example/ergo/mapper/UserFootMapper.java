package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.UserFoot;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户足迹表(UserFoot)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-24 03:09:47
 */
@Mapper
public interface UserFootMapper extends BaseMapper<UserFoot> {

}
