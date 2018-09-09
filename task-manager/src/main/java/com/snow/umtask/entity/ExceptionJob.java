package com.snow.umtask.entity;

import javax.persistence.*;

import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Entity
@Table(name = "t_exception_job")
public class ExceptionJob {

    private int id;
    private String jobKey;
    private Timestamp occurredTime;
    private String exceptionContent;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "job_key")
    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    @Column(name = "occurred_time")
    public Timestamp getOccurredTime() {
        return occurredTime;
    }

    public void setOccurredTime(Timestamp occurredTime) {
        this.occurredTime = occurredTime;
    }

    @Column(name = "exception_content")
    public String getExceptionContent() {
        return exceptionContent;
    }

    public void setExceptionContent(String exceptionContent) {
        this.exceptionContent = exceptionContent;
    }
}