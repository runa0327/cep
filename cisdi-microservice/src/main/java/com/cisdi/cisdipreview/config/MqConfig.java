package com.cisdi.cisdipreview.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@ConfigurationProperties(prefix = "mq-config")
public class MqConfig {

    @Value("${mq-config.xMaxLength}")
    private int xMaxLength;

    @Value("${qygly.ext-rest-helper.login-keeper.orgCode}")
    private String orgId;

    @Bean
    public Queue directQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置队列最大长度为200
        args.put("x-max-length", xMaxLength);
//        String orgId = LoginInfoManager.loginInfo.currentOrgInfo.id;
        return new Queue("inspection.req." + orgId, true, false, false, args);
    }

    @Bean
    public Queue rspQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置队列最大长度为200
        args.put("x-max-length", xMaxLength);
//        String orgId = LoginInfoManager.loginInfo.currentOrgInfo.id;
        return new Queue("inspection.rsp." + orgId, true, false, false, args);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean("error_direct")
    public DirectExchange errorMessageExchange(){
        return new DirectExchange("error_direct");
    }
    @Bean("error_queue")
    public Queue errorQueue(){
        return new Queue("error_rsp_queue",true);
    }
    @Bean
    public Binding bindingError(DirectExchange error_direct, Queue error_queue){
        return BindingBuilder.bind(error_queue).to(error_direct).with("error_rsp");
    }
    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate,"error_direct","error_rsp");
    }

//    public void setxMaxLength(int xMaxLength) {
//        this.xMaxLength = xMaxLength;
//    }

//    public void setOrgId(String orgId) {
//        this.orgId = orgId;
//    }

//    @Bean
//    public SimpleRabbitListenerContainerFactory blockQueue(ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPrefetchCount(1); // 设置prefetchCount为1
//        return factory;
//    }
}
