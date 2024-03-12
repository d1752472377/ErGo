package com.example.ergo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ergo.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * (Schedulejob)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-12 23:30:34
 */
@Mapper
@Repository
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
    List<ScheduleJob> getJobList();

    ScheduleJob getJobById(Long jobId);

    int saveJob(ScheduleJob scheduleJob);

    int updateJob(ScheduleJob scheduleJob);

    int deleteJobById(Long jobId);

    int updateJobStatusById(Long jobId, Boolean status);

}
