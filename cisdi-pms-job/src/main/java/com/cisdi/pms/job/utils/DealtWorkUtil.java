package com.cisdi.pms.job.utils;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.config.MqttConfig;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一代办接口工具
 * @author dlt
 * @date 2023/4/3 周一
 */
@Slf4j
@Component
public class DealtWorkUtil {

    /**
     * 新增代办
     * @param userId 用户id 平台方
     * @param title 代办标题
     * @param remindContent 代办内容
     */
    public static Map<String,Object> addRemind(String userId, String title, String remindContent,MqttClient client,MqttConfig config){
        Map result = new HashMap<String,Object>();
        JSONObject json = new JSONObject();
        String id = IdUtil.getSnowflakeNextIdStr();
        json.put("id",id);
        json.put("userId",userId);
        json.put("appId", config.getAppId());
        json.put("title",title);
        json.put("appName",config.getAppName());
        json.put("content",remindContent);
        json.put("callbackUrl",config.getCallbackUrl());
        String content = json.toString();

        int qos = 1;

        try {
            // 创建消息并设置 QoS
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // 发布消息
            client.publish(config.getRemindAddTopic(), message);
            log.info("Message published");
            log.info("topic: " + config.getRemindAddTopic());
            log.info("message content: " + content);


            result.put("id",id);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 消息代办更新为 已读
     * @param remindId
     */
    public static void readRemind(String remindId,MqttClient client,MqttConfig config){
        if (Strings.isNullOrEmpty(remindId)){
            return;
        }
        JSONObject json = new JSONObject();
        json.put("id",remindId);
        json.put("appId",config.getAppId());
        String content = json.toString();
        int qos = 1;
        try {
            // 创建消息并设置 QoS
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // 发布消息
            client.publish(config.getRemindReadTopic(), message);
            log.info("Message published");
            log.info("topic: " + config.getRemindReadTopic());
            log.info("message content: " + content);

        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }


    public static MqttClient getClient(MqttConfig config) throws MqttException {
        String clientId = MqttClient.generateClientId();
        MqttClient client = new MqttClient(config.getBroker(), clientId, new MemoryPersistence());
        // 连接参数
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置用户名和密码
        options.setUserName(config.getUsername());
        options.setPassword(config.getPassword().toCharArray());
        options.setKeepAliveInterval(60);
        options.setConnectionTimeout(60);
        // 连接
        client.connect(options);
        return client;
    }

    public static void disconnect(MqttClient client){
        if (client.isConnected()){
            try {
                // 断开连接
                client.disconnect();
                // 关闭客户端
                client.close();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}
