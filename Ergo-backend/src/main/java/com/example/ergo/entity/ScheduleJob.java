package com.example.ergo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Schedulejob)表实体类
 *
 * @author makejava
 * @since 2024-03-12 23:30:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleJob {
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY"; //任务调度参数key

    private Long jobId;//任务id
    private String beanName;//spring bean名称
    private String methodName;//方法名
    private String params;//参数
    private String cron;//cron表达式
    private Boolean status;//任务状态
    private String remark;//备注
    private Date createTime;//创建时间
}
