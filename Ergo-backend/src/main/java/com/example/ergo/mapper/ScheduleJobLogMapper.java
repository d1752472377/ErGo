package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.ScheduleJobLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * (ScheduleJobLog)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-12 23:38:34
 */
@Mapper
@Repository
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLog> {
    List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate);

    int saveJobLog(ScheduleJobLog jobLog);

    int deleteJobLogByLogId(Long logId);
}
