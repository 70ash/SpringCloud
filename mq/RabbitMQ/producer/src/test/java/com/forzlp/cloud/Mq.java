package com.forzlp.cloud;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author 70ash
 * Date 2024/5/21 下午7:17
 * Description:
 */
@Slf4j
@SpringBootTest
public class Mq {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void SendMsg(){
        String exchangeName = "amq.fanout";
        String msg = "hahaha";
        System.out.println(rabbitTemplate);
        rabbitTemplate.convertAndSend(exchangeName,"", msg);
    }

    @Test
    public void SendFanoutMsg(){
        String exchangeName = "v1.fanout";
        String msg = "fanout";
        System.out.println(rabbitTemplate);
        rabbitTemplate.convertAndSend(exchangeName,"", msg);
    }

    @Test
    public void SendTopicMsg(){
        String exchangeName = "v1.topic";
        String msg = "topic";
        String routingKey = "topic.queue2";
        System.out.println(rabbitTemplate);
        rabbitTemplate.convertAndSend(exchangeName,routingKey, msg);
    }
    @Test
    public void sendManyMsg() {
        String queueName = "simple.queue";
        Message build = MessageBuilder.withBody("hello".getBytes())
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        // 发送三十万条消息
        for (int i = 0; i < 3000; i++) {
            rabbitTemplate.convertAndSend(queueName, build);
        }
        // rabbitTemplate.send(exchangeName,"", build);
    }
    @Test
    public void sendReturnConfirmMsg() {
        String exchangeName = "v1.topic";
        String msg = "topic";
        String routingKey = "topic.queue2";
        System.out.println(rabbitTemplate);
        rabbitTemplate.convertAndSend(exchangeName,routingKey, msg);
    }

    /**
     * 测试RabbitMQ消息的持久性
     */
    @Test
    public void sendTransmitMsg() {
        String exchangeName = "v1.fanout";
        String msg = "fanout";
        String routingKey = "";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
    }

    /**
     * 测试RabbitMQ消费者重试机制
     */
    @Test
    public void sendConsumerRetryMsg() {
        String exchangeName = "v1.topic";
        String msg = "trytry";
        String routingKey = "mq-demo:retry-topic";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg);
    }

    /**
     * 测试发送带有过期时间的消息
     */
    @Test
    public void sendTTLMsg() {
        String exchangeName = "test2.exchange";
        String msg = "trytry";
        String routingKey = "hi";
        rabbitTemplate.convertAndSend(exchangeName, routingKey, msg, message -> {
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
        log.info("消息发送成功");
    }
}
