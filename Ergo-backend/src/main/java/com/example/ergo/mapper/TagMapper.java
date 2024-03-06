package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * 标签管理表(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-12-31 22:07:34
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
