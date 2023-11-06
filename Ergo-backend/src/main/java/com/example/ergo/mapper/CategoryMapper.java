package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 类目管理表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-16 23:30:48
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {



}
