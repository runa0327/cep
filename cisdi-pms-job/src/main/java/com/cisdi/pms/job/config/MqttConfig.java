package com.cisdi.pms.job.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dlt
 * @date 2023/4/23 周日
 */
@Component
@Data
public class MqttConfig {
    //mq用户名
    @Value("${dealt_work_job.mq.username}")
    private String username;

    //mq密码
    @Value("${dealt_work_job.mq.password}")
    private String password;

    //mq代理
    @Value("${dealt_work_job.mq.broker}")
    private String broker;

    //应用名称
    @Value("${dealt_work_job.app_name}")
    private String appName;

    //应用id
    @Value("${dealt_work_job.app_id}")
    private String appId;

    //新增代办的接口地址后缀
    @Value("${dealt_work_job.mq.remind_add_topic}")
    private String remindAddTopic;

    //代办变已读的接口地址后缀
    @Value("${dealt_work_job.mq.remind_read_topic}")
    private String remindReadTopic;

    //回调地址
    @Value("${dealt_work_job.callback_url}")
    private String callbackUrl;

}
