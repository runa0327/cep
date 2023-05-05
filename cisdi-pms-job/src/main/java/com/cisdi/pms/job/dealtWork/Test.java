package com.cisdi.pms.job.dealtWork;

/**
 * @author dlt
 * @date 2023/4/3 周一
 */

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) {
        testAdd();
//        testRead();
    }

    public static void testAdd(){
        String broker = "tcp://59.50.38.27:1663";
        String topic = "/api/v1/remind/add";
        String username = "yazhoubayusercenter";
        String password = "fb7614f9ed314a9285bc502dd24d3e2a";
        String clientId = MqttClient.generateClientId();
        JSONObject json = new JSONObject();
        json.put("id", IdUtil.getSnowflakeNextIdStr());
        json.put("userId","62a821ff207464112c124a23");
        json.put("appId","61efaced4141ed89deeecbdb");
        json.put("title","测试标题");
        json.put("appName","工程项目信息协同系统");
        json.put("content","测试消息内容");
        json.put("callbackUrl","http://139.159.138.11:9090/pc/unifiedLogin?redirect=/pc/process&activeName=2");
        String content = json.toString();

        int qos = 1;

        try {
            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            // 连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置用户名和密码
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setKeepAliveInterval(60);
            options.setConnectionTimeout(60);
            // 连接
            client.connect(options);
            // 创建消息并设置 QoS
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // 发布消息
            client.publish(topic, message);
            System.out.println("Message published");
            System.out.println("topic: " + topic);
            System.out.println("message content: " + content);
            // 断开连接
            client.disconnect();
            // 关闭客户端
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
    public static void testRead(){
        String broker = "tcp://59.50.38.27:1663";
        String topic = "/api/v1/remind/read";
        String username = "yazhoubayusercenter";
        String password = "fb7614f9ed314a9285bc502dd24d3e2a";
        String clientId = MqttClient.generateClientId();
        JSONObject json = new JSONObject();
        json.put("id","1650042130040266752");
        json.put("appId","61efaced4141ed89deeecbdb");
        String content = json.toString();
        int qos = 1;
        try {
            MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
            // 连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置用户名和密码
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setKeepAliveInterval(60);
            options.setConnectionTimeout(60);
            // 连接
            client.connect(options);
            // 创建消息并设置 QoS
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // 发布消息
            client.publish(topic, message);
            System.out.println("Message published");
            System.out.println("topic: " + topic);
            System.out.println("message content: " + content);
            // 断开连接
            client.disconnect();
            // 关闭客户端
            client.close();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }


}
