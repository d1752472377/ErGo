package com.example.ergo.util.quartz;


import com.example.ergo.entity.ScheduleJobLog;
import com.example.ergo.service.ScheduleJobService;
import com.example.ergo.util.common.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.example.ergo.entity.ScheduleJob.JOB_PARAM_KEY;


/**
 * @author autor
 * @date 2024/3/12
 * 描述：定时任务执行与结果记录
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
    private ExecutorService service = Executors.newSingleThreadExecutor();
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        com.example.ergo.entity.ScheduleJob scheduleJob = (com.example.ergo.entity.ScheduleJob) context.getMergedJobDataMap().get(JOB_PARAM_KEY);
        //获取spring bean
        ScheduleJobService scheduleJobService = (ScheduleJobService) SpringContextUtils.getBean("scheduleJobServiceImpl");
        //数据库保存任务执行记录
        //数据库保存任务执行记录
        ScheduleJobLog jobLog = new ScheduleJobLog();
        jobLog.setJobId(scheduleJob.getJobId());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setMethodName(scheduleJob.getMethodName());
        jobLog.setParams(scheduleJob.getParams());
        jobLog.setCreateTime(new Date());
        //任务开始时间
        long startTime = System.currentTimeMillis();
        //执行任务
        log.info("任务准备执行，任务ID：{}", scheduleJob.getJobId());
        try {
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            future.get();
            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes((int) times);
            //任务执行结果
            jobLog.setStatus(true);
            log.info("任务执行成功，任务ID：{}，总共耗时：{} 毫秒", scheduleJob.getJobId(), times);
        } catch (Exception e) {
            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTimes((int) times);
            //任务执行结果
            jobLog.setStatus(false);
            jobLog.setError(e.toString());
            log.error("任务执行失败，任务ID：{}", scheduleJob.getJobId(), e);
        } finally {
            scheduleJobService.saveJobLog(jobLog);
        }
    }
}
