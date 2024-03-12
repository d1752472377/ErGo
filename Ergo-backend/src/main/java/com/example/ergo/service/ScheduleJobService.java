package com.example.ergo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ergo.entity.ScheduleJob;
import com.example.ergo.entity.ScheduleJobLog;

import java.util.List;


/**
 * (Schedulejob)表服务接口
 *
 * @author makejava
 * @since 2024-03-12 23:30:34
 */
public interface ScheduleJobService extends IService<ScheduleJob> {
    List<ScheduleJob> getJobList();

    void saveJob(ScheduleJob scheduleJob);

    void updateJob(ScheduleJob scheduleJob);

    void deleteJobById(Long jobId);

    void runJobById(Long jobId);

    void updateJobStatusById(Long jobId, Boolean status);

    List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate);

    void saveJobLog(ScheduleJobLog log);

    void deleteJobLogByLogId(Long logId);
}

