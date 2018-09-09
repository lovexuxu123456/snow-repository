package com.snow.umtask.controller;

import com.alibaba.fastjson.JSON;
import com.snow.umtask.entity.EmailListenerInfo;
import com.snow.umtask.model.EmailPerson;
import com.snow.umtask.pagemodel.EmailListenerPm;
import com.snow.umtask.pagemodel.JobListenerPm;
import com.snow.umtask.service.EmailListenerInfoService;
import com.snow.umtask.service.JobListenerService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-listener")
public class JobListenerController {

    @Autowired
    private  JobListenerService jobListenerService;

    @Autowired
    private EmailListenerInfoService emailListenerInfoService;


    @GetMapping("all-job-listener")
    public ResponseEntity<List<String>> getAllJobListener() throws SchedulerException {
        return ResponseEntity.ok(jobListenerService.getAllListener());
    }

    @PostMapping("add-job-listener")
    public ResponseEntity<String> addJobListener(@RequestBody JobListenerPm jobListenerPm) throws SchedulerException {
        JobListenerPm fullJobListenerPm = jobListenerPm.makeJobListenerPm();
        if(jobListenerService.getJobListener(fullJobListenerPm.getListenerName()) == null){
            jobListenerService.addListener(fullJobListenerPm);
            return ResponseEntity.ok("成功");
        }else {
            return ResponseEntity.badRequest().body("这个定时任务监听器已经存在了!");
        }
    }


    @PostMapping("add-email-listener")
    public ResponseEntity<String> addEmailListener(@RequestBody EmailListenerInfo entity) throws SchedulerException {
        EmailListenerPm fullJobListenerPm = entity.makeJobListenerPm();
        if(jobListenerService.getJobListener(fullJobListenerPm.getListenerName()) == null){
            jobListenerService.addEmailListener(fullJobListenerPm);
            // 还需要的额外功能,将它增加到数据库中,在spring容器启动之后再从数据库加载回到系统.
            // Quartz 2.x 版本没有把我们自己写的监听器放进数据库中,所以重启之后会丢失者部分内容
            // 保存监听器信息

            List<EmailPerson> success = entity.getRecivePersons().get("success");
            List<EmailPerson> errors = entity.getRecivePersons().get("error");

            entity.setReceiveNormalPersonArray(JSON.toJSONString(success));
            entity.setReceiveExceptionPersonArray(JSON.toJSONString(errors));

            entity.setReceiveExceptionPersonArray(JSON.toJSONString(errors));
            emailListenerInfoService.addEmailListenerInfo(entity);
            return ResponseEntity.ok("成功");
        }else {
            return ResponseEntity.badRequest().body("这个定时任务监听器已经存在了!");
        }
    }

    @DeleteMapping("delete-job-listener/{listenerName}")
    public ResponseEntity<String> deleteJobListener(@PathVariable String listenerName) throws SchedulerException {
        if(jobListenerService.getJobListener(listenerName) != null){
            jobListenerService.removeJobListener(listenerName);
            // 还需要的额外功能,将它从数据库中移除掉,避免在spring容器启动之后再从数据库加载回到系统.
            return ResponseEntity.ok("Success");
        }else{
            return ResponseEntity.badRequest().body("这个定时任务监听器不存在!");
        }
    }
}
