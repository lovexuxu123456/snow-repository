package com.snow.umtask.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 获取档案系统待办列表
 */
public class SyncTaskList2 extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("这个是定时任务 [SyncTaskList2] ");
    }
}
