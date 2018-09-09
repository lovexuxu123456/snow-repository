package com.snow.umtask.controller;

import com.alibaba.fastjson.JSON;
import com.snow.umtask.pagemodel.ExecuteJobPm;
import com.snow.umtask.pagemodel.JobDetailPm;
import com.snow.umtask.service.SchedulerService;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class JobDetailController {

    private final SchedulerService schedulerService;

    @Autowired
    public JobDetailController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }


    /**
     * 获取全部的定时任务
     * @return
     * @throws SchedulerException
     */
    @GetMapping("all-job")
    public ResponseEntity getAllJob() throws SchedulerException {
        Map map = schedulerService.findAllJob();
        Map resultMap = new HashMap();
        resultMap.put("groups",map);
        return ResponseEntity.ok(resultMap);
    }


    /**
     * 增加一个定时任务
     * @param executeJobPm
     * @return
     * @throws SchedulerException
     */
    @PostMapping("add-execute-job-trigger")
    public ResponseEntity addExecuteJobTrigger(@RequestBody ExecuteJobPm executeJobPm) throws SchedulerException {
        JobKey jobKey = new JobKey(executeJobPm.getJobClassName(),executeJobPm.getJobGroup());
        if(!schedulerService.checkExistJobKey(jobKey)){
            return ResponseEntity.ok(schedulerService.addExecuteJobTrigger(executeJobPm));
        }else {
            schedulerService.deleteJob(jobKey);
            return ResponseEntity.ok(schedulerService.addExecuteJobTrigger(executeJobPm));
        }
    }


    /**
     * 暂停
     * @param jobClassName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    @GetMapping("pause-job/{jobClassName:.+}/{jobGroup}")
    public ResponseEntity pauseJob(@PathVariable String jobClassName, @PathVariable String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobClassName,jobGroup);
        if(schedulerService.checkExistJobKey(jobKey)){
            schedulerService.pauseJob(jobKey);
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.badRequest().body("这个定时任务不存在!");
        }
    }


    /**
     * 恢复定时任务
     * @param jobClassName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    @GetMapping("resume-job/{jobClassName:.+}/{jobGroup}")
    public ResponseEntity resumeJob(@PathVariable String jobClassName, @PathVariable String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobClassName,jobGroup);
        if(schedulerService.checkExistJobKey(jobKey)){
            schedulerService.resumeJob(jobKey);
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.badRequest().body("这个定时任务不存在!");
        }
    }


    /**
     * 立即执行
     * @param jobClassName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    @GetMapping("start-job/{jobClassName:.+}/{jobGroup}")
    public ResponseEntity startJob(@PathVariable String jobClassName, @PathVariable String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobClassName,jobGroup);
        if(schedulerService.checkExistJobKey(jobKey)){
            schedulerService.startJob(jobKey);
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.badRequest().body("这个定时任务不存在!");
        }

    }


    /**
     * 删除一个定时任务
     * @param jobClassName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
   @DeleteMapping("delete-job/{jobClassName:.+}/{jobGroup}")
    public ResponseEntity deleteJob(@PathVariable String jobClassName, @PathVariable String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobClassName,jobGroup);
        if(schedulerService.checkExistJobKey(jobKey)){
            return ResponseEntity.ok(schedulerService.deleteJob(jobKey));
        }else {
            return ResponseEntity.badRequest().body("这个定时任务不存在!");
        }
    }
}
