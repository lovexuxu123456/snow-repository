package com.snow.umtask.listener;


import com.alibaba.fastjson.JSON;
import com.snow.umtask.entity.EmailListenerInfo;
import com.snow.umtask.service.EmailListenerInfoService;
import com.snow.umtask.service.ExceptionJobService;
import com.snow.umtask.util.Constant;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobExceptionListener extends JobListenerSupport {

    Logger logger = LoggerFactory.getLogger(JobExceptionListener.class);

    @Autowired
    private ExceptionJobService exceptionJobService;

    @Autowired
    private EmailListenerInfoService emailListenerInfoService;

    private String name;

    public JobExceptionListener(String name){
        this.name = name;
    }

    public JobExceptionListener(){
        this(Constant.EXCEPTION_LISTENER);
    }


    /**
     * Scheduler 在 JobDetail 将要被执行时调用这个方法
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Scheduler 在 JobDetail 将要被执行时调用这个方法");
    }

    /**
     * Scheduler 在 JobDetail 即将被执行，但又被 TriggerListener否决了时调用这个方法。
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println("Job监听器：MyJobListener.jobToBeExecuted()");
    }

    /**
     * Scheduler 在 JobDetail 被执行之后调用这个方法。
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String simpleListenerName = context.getJobDetail().getJobClass().getSimpleName() + "_Then_" + "EMAIL";
        EmailListenerInfo info =  emailListenerInfoService.selectByPrimaryKey(simpleListenerName);
        if(jobException!=null)
        {
            System.out.println("Job邮件监听器：" + context.getJobDetail().getDescription() +"执行异常结束");
            if(info != null && info.getAllowSendExceptionEmail() == 1){
                System.out.println("我发送异常邮件了哦");
                System.out.println("参数："+JSON.toJSONString(info));
                System.out.println("异常收件人：" + JSON.toJSONString(info.getReceiveExceptionPersonArray()));
                System.out.println("正常收件人：" + JSON.toJSONString(info.getReceiveExceptionPersonArray()));
                System.out.println("是否允许发送异常邮件:" + info.getAllowSendExceptionEmail());
                System.out.println("是否允许发送正常结束邮件"  + info.getAllowSendNormalEmail());
            }



            //保存异常信息到数据库中
            exceptionJobService.saveExceptionJob(context.getJobDetail().getKey().toString(), jobException.getMessage());
            return;
        }
        if(info != null && info.getAllowSendNormalEmail() == 1){
            System.out.println("我发送正常结束的邮件了哦");
            System.out.println("参数："+JSON.toJSONString(info));
            System.out.println("异常收件人：" + JSON.toJSONString(info.getReceiveExceptionPersonArray()));
            System.out.println("正常收件人：" + JSON.toJSONString(info.getReceiveExceptionPersonArray()));
            System.out.println("是否允许发送异常邮件:" + info.getAllowSendExceptionEmail());
            System.out.println("是否允许发送正常结束邮件"  + info.getAllowSendNormalEmail());
        }
      System.out.println("Job邮件监听器：" + context.getJobDetail().getDescription() +"执行结束");
    }






    @Override
    public String getName() {
        return this.name;
    }
}