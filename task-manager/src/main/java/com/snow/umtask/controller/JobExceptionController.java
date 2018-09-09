package com.snow.umtask.controller;

import com.snow.umtask.entity.ExceptionJob;
import com.snow.umtask.model.PageResult;
import com.snow.umtask.service.ExceptionJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job-exception")
public class JobExceptionController {

    @Autowired
    private ExceptionJobService exceptionJobService;

    /**
     * 查询所有异常任务的信息
     *
     * @param pageable 分页信息
     * @return 任务的异常信息列表
     */
    @GetMapping("find-all")
    public ResponseEntity<PageResult> findAll(@PageableDefault(sort = "occurredTime", direction = Sort.Direction.DESC)
                                                      Pageable pageable) {
        return ResponseEntity.ok(exceptionJobService.findAll(pageable));
    }

    /**
     * 删除异常纪录
     *
     * @param id id
     * @return 删除成功消息
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            exceptionJobService.deleteExceptionJob(id);
            return ResponseEntity.ok("成功.");
        } catch (Exception e) {

        }
        //整个服务器内部异常返回去
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
