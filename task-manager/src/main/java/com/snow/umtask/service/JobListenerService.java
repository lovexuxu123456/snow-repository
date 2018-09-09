package com.snow.umtask.service;

import com.snow.umtask.config.SchedulerManager;
import com.snow.umtask.pagemodel.EmailListenerPm;
import com.snow.umtask.pagemodel.JobListenerPm;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class JobListenerService {

    private final Scheduler scheduler;

    private final SchedulerManager schedulerManager;

    @Autowired
    public JobListenerService(Scheduler scheduler, SchedulerManager schedulerManager) {
        this.scheduler = scheduler;
        this.schedulerManager = schedulerManager;
    }

    /**
     * 添加job监听
     * @param jobListenerPm job监听对象
     * @throws SchedulerException 异常
     */
    public void addListener(JobListenerPm jobListenerPm) throws SchedulerException {
        scheduler.getListenerManager().addJobListener(schedulerManager.makeChainedJobListener(jobListenerPm));
    }

    /**
     * 添加邮件监听,就是执行成功或者失败时发送邮件
     * @param emailListenerPm 邮件监听对象
     * @throws SchedulerException 异常
     */
    public void addEmailListener(EmailListenerPm emailListenerPm) throws SchedulerException {
        JobKey jobKey =  new JobKey(emailListenerPm.getJobClassName(), emailListenerPm.getJobGroup());
        scheduler.getListenerManager().addJobListener(schedulerManager.makeEmailJobListener(emailListenerPm), KeyMatcher.keyEquals(jobKey));
    }


    /**
     *
     * @return 返回所有监听事件
     * @throws SchedulerException 异常
     */
    public List<String> getAllListener() throws SchedulerException {
        List<JobListener> jobListeners = scheduler.getListenerManager().getJobListeners();
        List<String> jobListenerNames = new ArrayList<>();
        for (JobListener jobListener : jobListeners) {
            jobListenerNames.add(jobListener.getName());
        }
        return jobListenerNames;
    }

    /**
     * 删除监听
     * @param listenerName 监听名称
     * @throws SchedulerException 异常
     */
    public void removeJobListener(String listenerName) throws SchedulerException {
        scheduler.getListenerManager().removeJobListener(listenerName);
    }

    /**
     * 获取监听对象
     * @param listenerName 监听名
     * @return 监听对象
     * @throws SchedulerException 异常
     */
    public JobListener getJobListener(String listenerName) throws SchedulerException {
        return scheduler.getListenerManager().getJobListener(listenerName);
    }
}
