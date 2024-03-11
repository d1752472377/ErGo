package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.ReadCount;
import com.example.ergo.mapper.ReadCountMapper;
import com.example.ergo.service.ReadCountService;
import org.springframework.stereotype.Service;

/**
 * 计数表(ReadCount)表服务实现类
 *
 * @author makejava
 * @since 2024-03-11 15:15:37
 */
@Service("readCountService")
public class ReadCountServiceImpl extends ServiceImpl<ReadCountMapper, ReadCount> implements ReadCountService {

}

