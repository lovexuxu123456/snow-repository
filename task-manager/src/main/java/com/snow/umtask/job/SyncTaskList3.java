package com.snow.umtask.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 获取ITIL系统待办任务列表
 */
public class SyncTaskList3 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("这个是定时任务 [SyncTaskList3] ");
    }
}
