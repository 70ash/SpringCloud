package com.forzlp.cloud.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Author 70ash
 * Date 2024/5/25 下午2:43
 * Description: Rabbit消费者失败重试机制配置
 */

@Configuration
@AllArgsConstructor
// 开启这个配置时，这个配置类才会生效
@ConditionalOnProperty(prefix = "spring.rabbitmq.listener.simple.retry", name = "enabled", havingValue = "true")
public class RabbitMQConsumerRetryConfig {

    private RabbitTemplate rabbitTemplate;
    @Bean
    public DirectExchange errorExchange() {
        return new DirectExchange("error.exchange");
    }
    @Bean
    public Queue errorQueue() {
        return new Queue("error.queue");
    }
    @Bean
    public Binding errorBinding(DirectExchange errorExchange, Queue errorQueue) {
        return BindingBuilder
                .bind(errorQueue)
                .to(errorExchange)
                .with("error");
    }

    @Bean
    public MessageRecoverer messageRecoverer() {
        return new RepublishMessageRecoverer(rabbitTemplate, "error.exchange", "error");
    }
}
