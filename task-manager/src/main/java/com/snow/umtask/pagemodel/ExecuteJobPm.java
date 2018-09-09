package com.snow.umtask.pagemodel;

import com.snow.umtask.util.UtilTools;
import org.quartz.Job;


public class ExecuteJobPm {

    private String jobClassName;

    private String jobGroup;

    private Class<? extends Job> jobClass;

    private String cronExpression;

    private String description;

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass == null ? UtilTools.getJobClass(this.jobClassName) : jobClass;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
