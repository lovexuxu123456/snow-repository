package com.snow.umtask.service;

import com.snow.umtask.entity.EmailListenerInfo;
import com.snow.umtask.mapper.EmailListenerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 邮件监听信息存储服务
 */
@Service
public class EmailListenerInfoService {

    @Autowired
    private EmailListenerInfoMapper emailListenerInfoMapper;

    /**
     * 持久化到数据库
     * @param info
     * @return
     */
    public int addEmailListenerInfo(EmailListenerInfo info){
       return emailListenerInfoMapper.insert(info);
    }

    /**
     *  取消持久化状态
     * @param primaryKey
     * @return
     */
    public int deleteEmailListenerInfo(String primaryKey){
        return emailListenerInfoMapper.deleteByPrimaryKey(primaryKey);
    }

    public EmailListenerInfo selectByPrimaryKey(String simpleListenerName) {
        return emailListenerInfoMapper.selectByPrimaryKey(simpleListenerName);
    }

}
