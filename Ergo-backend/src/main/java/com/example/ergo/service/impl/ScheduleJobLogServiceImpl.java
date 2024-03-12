package com.example.ergo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ergo.entity.ScheduleJobLog;
import com.example.ergo.mapper.ScheduleJobLogMapper;
import com.example.ergo.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;

/**
 * (ScheduleJobLog)表服务实现类
 *
 * @author makejava
 * @since 2024-03-12 23:38:34
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLog> implements ScheduleJobLogService {

}

