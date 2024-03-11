package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.ReadCount;
import org.apache.ibatis.annotations.Mapper;


/**
 * 计数表(ReadCount)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-11 15:15:34
 */
@Mapper
public interface ReadCountMapper extends BaseMapper<ReadCount> {

}
