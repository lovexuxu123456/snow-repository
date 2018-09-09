package com.snow.umtask.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 获取核心待办任务的定时器
 */
//
public class SyncTaskList1 extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("这个是定时任务 [SyncTaskList1] ");
    }
}
