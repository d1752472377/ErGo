package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.UserRelation;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户关系表(UserRelation)表数据库访问层
 *
 * @author makejava
 * @since 2024-02-23 19:08:27
 */
@Mapper
public interface UserRelationMapper extends BaseMapper<UserRelation> {

}
