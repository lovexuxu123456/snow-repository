package com.snow.umtask.entity;


import com.snow.umtask.model.EmailPerson;
import com.snow.umtask.pagemodel.EmailListenerPm;
import com.snow.umtask.util.UtilTools;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * 关于这个类的初衷:
 *      我们的定时器目前是不能持久化的,所以需要借助这样的机制来自己实现某个种类的监听器的存储机制
 *      这个类,是为了适应我们邮件监听器的特殊情况,在这个监听器监听的job类执行过程中是需要发送邮件的.邮件相关的信息需要存储
 */
@Entity
@Table(name = "t_email_listener_info")
public class EmailListenerInfo {

    //收件人列表,这个不能存放到数据库所以忽略掉
    @Transient
    private Map<String,List<EmailPerson>> recivePersons;

    public Map<String, List<EmailPerson>> getRecivePersons() {
        return recivePersons;
    }

    @Id
    @Column(name="listener_name")
    private String listenerName;

    @Column(name="job_class_name")
    private String jobClassName;

    @Column(name="job_group_name")
    private String jobGroupName;

    @Column(name="receive_normal_person")
    private String receiveNormalPersonArray;

    @Column(name="receive_exception_person")
    private String receiveExceptionPersonArray;

    @Column(name="allow_send_normal_email")
    private int allowSendNormalEmail;

    @Column(name="allow_send_exception_email")
    private int allowSendExceptionEmail;

    @Column(name="send_person")
    private String sendPerson;

    @Column(name="error_email_template")
    private String errorEmailTemplate;

    @Column(name="normal_email_template")
    private String normalEmailTemplate;

    public void setRecivePersons(Map<String, List<EmailPerson>> recivePersons) { this.recivePersons = recivePersons; }
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
    public String getJobGroupName() {
        return jobGroupName;
    }
    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }
    public String getReceiveNormalPersonArray() {
        return receiveNormalPersonArray;
    }
    public void setReceiveNormalPersonArray(String receiveNormalPersonArray) { this.receiveNormalPersonArray = receiveNormalPersonArray; }
    public String getReceiveExceptionPersonArray() {
        return receiveExceptionPersonArray;
    }
    public void setReceiveExceptionPersonArray(String receiveExceptionPersonArray) { this.receiveExceptionPersonArray = receiveExceptionPersonArray; }
    public int getAllowSendNormalEmail() {
        return allowSendNormalEmail;
    }
    public void setAllowSendNormalEmail(int allowSendNormalEmail) {
        this.allowSendNormalEmail = allowSendNormalEmail;
    }
    public int getAllowSendExceptionEmail() {
        return allowSendExceptionEmail;
    }
    public void setAllowSendExceptionEmail(int allowSendExceptionEmail) { this.allowSendExceptionEmail = allowSendExceptionEmail; }
    public String getSendPerson() {
        return sendPerson;
    }
    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }
    public String getErrorEmailTemplate() {
        return errorEmailTemplate;
    }
    public void setErrorEmailTemplate(String errorEmailTemplate) {
        this.errorEmailTemplate = errorEmailTemplate;
    }
    @Column(name="normal_email_template")
    public String getNormalEmailTemplate() {
        return normalEmailTemplate;
    }
    public void setNormalEmailTemplate(String normalEmailTemplate) {
        this.normalEmailTemplate = normalEmailTemplate;
    }


    /**
     * @return 整合的Job监听对象
     */
    public EmailListenerPm makeJobListenerPm(){
        EmailListenerPm emailListenerPm = new EmailListenerPm();
        emailListenerPm.setJobClassName(this.jobClassName);
        emailListenerPm.setJobGroup(this.jobGroupName);
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
