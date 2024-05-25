package com.forzlp.cloud.listen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Author 70ash
 * Date 2024/5/21 下午9:13
 * Description:
 */
@Slf4j
@Component
public class MyRabbitListener {
    @RabbitListener(queues = "test.queue")
    public void listenRabbitMQ(String msg) {
        System.out.println("接收到消息" + msg);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "fanout.queue1", durable = "true"),
            exchange = @Exchange(name = "v1.fanout", type = ExchangeTypes.FANOUT)
    ))
    public void listenFanoutMessage(String msg) {
        System.out.println("fanout接收到消息" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1", durable = "true"),
            exchange = @Exchange(name = "v1.topic", type = ExchangeTypes.TOPIC),
            key = "topic.queue1"
    ))
    public void listenTopic1Message(String msg) {
        System.out.println("topic1接收到消息" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2", durable = "true"),
            exchange = @Exchange(name = "v1.topic", type = ExchangeTypes.TOPIC),
            key = "topic.queue2"
    ))
    public void listenTopic2Message(String msg) {
        System.out.println("topic2接收到消息" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    name = "lazy.queue2",
                    durable = "true",
                    arguments = @Argument(name = "x-queue-mode", value = "lazy")
            ),
            exchange = @Exchange(name = "lazy.exchange", type = ExchangeTypes.FANOUT)

    ))
    public void listenLazyQueue(String msg) {
        System.out.println("lazy.queue" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "retry.queue", durable = "true"),
            exchange = @Exchange(name = "v1.topic", type = ExchangeTypes.TOPIC),
            key = "mq-demo:retry-topic"
    ))
    public void listenRetryQueue(String msg) throws Exception {
        System.out.println("retry.queue" + msg);
        throw new Exception("故意的");
    }

    @RabbitListener(queues = "dle.queue")
    public void listenDLEQueue(String msg) {
        System.out.println("dle.queue" + msg);
        log.info("接收到私信队列" + msg);
    }
}
