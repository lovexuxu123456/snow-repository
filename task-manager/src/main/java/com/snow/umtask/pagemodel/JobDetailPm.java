package com.snow.umtask.pagemodel;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import java.util.List;

public class JobDetailPm {

    public String jobKey;

    public String jobGroup;

    public String jobClassName;

    public String description;

    private CronTrigger tigger;

    Trigger.TriggerState triggerState;

    public Trigger.TriggerState getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(Trigger.TriggerState triggerState) {
        this.triggerState = triggerState;
    }

    public CronTrigger getTigger() {
        return tigger;
    }

    public void setTigger(CronTrigger tigger) {
        this.tigger = tigger;
    }

    public boolean isDurable;

    public boolean isConcurrentExectionDisallowed;

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDurable() {
        return isDurable;
    }

    public void setDurable(boolean durable) {
        isDurable = durable;
    }

    public boolean isConcurrentExectionDisallowed() {
        return isConcurrentExectionDisallowed;
    }

    public void setConcurrentExectionDisallowed(boolean concurrentExectionDisallowed) {
        isConcurrentExectionDisallowed = concurrentExectionDisallowed;
    }

    public static JobDetailPm makeJobDetailPm(JobDetail jobDetail){
        JobDetailPm jobDetailPm = new JobDetailPm();
        jobDetailPm.setConcurrentExectionDisallowed(jobDetail.isConcurrentExectionDisallowed());
        jobDetailPm.setDescription(jobDetail.getDescription());
        jobDetailPm.setDurable(jobDetail.isDurable());
        jobDetailPm.setJobClassName(jobDetail.getJobClass().getName());
        jobDetailPm.setJobKey(jobDetail.getKey().getName());
        jobDetailPm.setJobGroup(jobDetail.getKey().getGroup());





        return jobDetailPm;
    }
}
