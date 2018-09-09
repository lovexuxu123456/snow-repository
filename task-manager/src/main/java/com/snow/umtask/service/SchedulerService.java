package com.snow.umtask.service;

import com.snow.umtask.pagemodel.ExecuteJobPm;
import com.snow.umtask.pagemodel.JobDetailPm;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SchedulerService {

    private final Scheduler scheduler;

    @Autowired
    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 删除Job
     * @param jobKey JobKey
     * @throws SchedulerException 异常
     */
    public boolean deleteJob(JobKey jobKey) throws SchedulerException {
        return scheduler.deleteJob(jobKey);
    }


    /**
     * 暂停任务
     * @param jobKey
     * @throws SchedulerException
     */
    public void pauseJob(JobKey jobKey) throws SchedulerException {
        scheduler.pauseJob(jobKey);
    }


    /**
     * 恢复任务
     * @param jobKey
     * @throws SchedulerException
     */
    public void resumeJob(JobKey jobKey) throws SchedulerException {
        scheduler.resumeJob(jobKey);
    }


    /**
     * 获取所有 JobDetail
     * @return 所有分组的JobDetail
     * @throws SchedulerException 异常
     */
    public Map<String, List<JobDetailPm>> findAllJob() throws SchedulerException {
        List<String> jobGroupNames = scheduler.getJobGroupNames();
        Map<String, List<JobDetailPm>> map = new HashMap<>();
        for (String group : jobGroupNames) {
            Set<JobKey> jobKeySet = getJobKeySet(group);
            List<JobDetailPm> jobDetailList = getJobDetailList(jobKeySet);
            map.put(group, jobDetailList);
        }
        return map;
    }

    /**
     * 获取jobKeySet
     * @param group 分组名
     * @return 所有jobKey
     */
    private Set<JobKey> getJobKeySet(String group){
        try {
            return scheduler.getJobKeys(GroupMatcher.jobGroupEquals(group));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取job列表
     * @param jobKeySet 所有jobKey
     * @return job列表
     */
    private List<JobDetailPm> getJobDetailList(Set<JobKey> jobKeySet) {
        List<JobDetailPm> jobDetailPms = new ArrayList<>();
        for (JobKey jobKey : jobKeySet) {
            jobDetailPms.add(getJobDetail(jobKey));
        }
        return jobDetailPms;
    }

    /**
     * 获取job
     * @param jobKey jobKey
     * @return JobDetail
     */
    private JobDetailPm getJobDetail(JobKey jobKey){
        try {
            //获取到触发器相关信息
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            JobDetailPm jobDetailPm = JobDetailPm.makeJobDetailPm(scheduler.getJobDetail(jobKey));
            if (triggers != null && triggers.size() > 0) {
                //可能有很多，但是我只处理了一个
                for (Trigger trigger : triggers) {
                    if(trigger instanceof  CronTrigger){
                        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                        jobDetailPm.setTigger((CronTrigger) trigger);
                        jobDetailPm.setTriggerState(triggerState);
                    }
                }
            }
            return jobDetailPm;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 添加执行任务，cronTrigger
     * @param executeJobPm 执行的任务信息
     * @return 日期
     * @throws SchedulerException 异常
     */
    public Date addExecuteJobTrigger(ExecuteJobPm executeJobPm) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(executeJobPm.getJobClass())
                .withDescription(executeJobPm.getDescription())
                .withIdentity(executeJobPm.getJobClassName(), executeJobPm.getJobGroup())
                .build();

        job.getJobDataMap().put("likePerson", "小姐姐");
        job.getJobDataMap().put("likeFood", "好吃的");

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withDescription(executeJobPm.getDescription())
                .withIdentity(executeJobPm.getJobClassName(),executeJobPm.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(executeJobPm.getCronExpression())).build();
       return scheduler.scheduleJob(job,trigger);
    }

    /**
     * 检查job是否存在
     * @param jobKey jobKey
     * @return 返回boolean
     * @throws SchedulerException 异常
     */
    public boolean checkExistJobKey(JobKey jobKey) throws SchedulerException {
        return scheduler.checkExists(jobKey);
    }

    /**
     * 启动一个任务
     * @param jobKey
     * @throws SchedulerException
     */
    public void startJob(JobKey jobKey) throws SchedulerException {
         scheduler.triggerJob(jobKey);
    }
}
