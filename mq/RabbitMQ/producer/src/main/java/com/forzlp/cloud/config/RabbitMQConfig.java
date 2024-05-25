// package com.forzlp.cloud.config;
//
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.BeansException;
// import org.springframework.context.ApplicationContext;
// import org.springframework.context.ApplicationContextAware;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// @Slf4j
// public class RabbitMQConfig implements ApplicationContextAware {
//     @Override
//     public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//         RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
//         rabbitTemplate.setConfirmCallback(
//                 (correlationData, ack, cause) -> {
//                     if (ack) {
//                         // 消息投递成功
//                         log.debug("消息投递成功");
//                     } else {
//                         // 消息投递失败
//                         log.info("消息投递失败" + cause);
//                     }
//                 });
//     }
// }