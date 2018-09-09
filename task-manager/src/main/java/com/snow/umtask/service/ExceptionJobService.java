package com.snow.umtask.service;

import com.github.pagehelper.PageHelper;
import com.snow.umtask.entity.ExceptionJob;
import com.snow.umtask.mapper.ExceptionJobMapper;
import com.snow.umtask.model.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * .
 * Date 2017-03-31
 *
 * @author Medxi
 * @version V1.0
 */
@Service
public class ExceptionJobService {

    @Autowired
    private ExceptionJobMapper exceptionJobMapper;

    /**
     * 保存异常任务纪录
     *
     * @param jobKey           任务的key
     * @param exceptionContent 异常内容
     */
    public void saveExceptionJob(String jobKey, String exceptionContent) {
        ExceptionJob exceptionJob = new ExceptionJob();
        exceptionJob.setJobKey(jobKey);
        exceptionJob.setExceptionContent(exceptionContent);
        exceptionJobMapper.insert(exceptionJob);
    }

    /**
     * 查询所有异常任务信息
     *
     * @param pageable 分页对象
     * @return 任务的异常信息列表
     */
    public PageResult findAll(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        com.github.pagehelper.Page<ExceptionJob> page = (com.github.pagehelper.Page<ExceptionJob>) exceptionJobMapper.select(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除异常纪录
     *
     * @param id id
     */
    public void deleteExceptionJob(Integer id) {
        exceptionJobMapper.deleteByPrimaryKey(id);
    }
}
