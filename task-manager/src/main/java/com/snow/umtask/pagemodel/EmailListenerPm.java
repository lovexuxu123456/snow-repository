package com.snow.umtask.pagemodel;

import com.snow.umtask.model.EmailPerson;
import com.snow.umtask.util.UtilTools;
import org.quartz.Job;

import java.util.List;
import java.util.Map;


public class EmailListenerPm {

    private String listenerName;

    private String jobClassName;

    private String jobGroup;

    private Class<? extends Job> jobClass;


    //收件人列表
   private Map<String,List<EmailPerson>>  recivePersons;

   //发件人信息
   private EmailPerson sendPerson;

    public EmailPerson getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(EmailPerson sendPerson) {
        this.sendPerson = sendPerson;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

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

    public Map<String, List<EmailPerson>> getRecivePersons() {
        return recivePersons;
    }

    public void setRecivePersons(Map<String, List<EmailPerson>> recivePersons) {
        this.recivePersons = recivePersons;
    }


    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends Job> masterJobClass) {
        this.jobClass = masterJobClass;
    }

    /**
     * @return 整合的Job监听对象
     */
    public EmailListenerPm makeJobListenerPm(){
        EmailListenerPm emailListenerPm = new EmailListenerPm();
        emailListenerPm.setJobClassName(this.jobClassName);
        emailListenerPm.setJobGroup(this.jobGroup);
        emailListenerPm.setJobClass(UtilTools.getJobClass(this.jobClassName));
        emailListenerPm.setListenerName(getTheJobListenerName(emailListenerPm));
        return emailListenerPm;
    }

    /**
     * @param emailListenerPm JobListenerPm对象
     * @return JobListenerPm的名字
     */
    private String getTheJobListenerName(EmailListenerPm emailListenerPm){
        String simpleJobClassName = emailListenerPm.getJobClass().getSimpleName();
        return simpleJobClassName + "_Then_" + "EMAIL";
    }

}
