package com.snow.umtask.config;

import com.snow.umtask.listener.JobExceptionListener;
import org.quartz.Trigger;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.text.ParseException;

@Configuration
public class SchedulerConfig {

    private final JobExceptionListener jobExceptionListener;
    private final SchedulerManager schedulerManager;

    @Autowired
    public SchedulerConfig(SchedulerManager schedulerManager, JobExceptionListener jobExceptionListener) {
        this.schedulerManager = schedulerManager;
        this.jobExceptionListener = jobExceptionListener;
    }

    private Trigger[] assembleTriggers() throws ParseException {
       return new Trigger[]{};
    }

    private JobListenerSupport[] assembleJobListeners() {
        return new JobListenerSupport[]{jobExceptionListener};
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(@Qualifier("dataSourceForUmtask") DataSource dataSource) throws ParseException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setTriggers(assembleTriggers());
        schedulerFactoryBean.setGlobalJobListeners(assembleJobListeners());
        return schedulerFactoryBean;
    }
}
