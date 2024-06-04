package com.trong.rabbitmqservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //Using fanout don't need routingKey
//    @Value("${rabbit.config.routingKey}")
//    private String routingKey;
    @Value("${rabbitmq.exchange.fanout-exchange}")
    private String fanoutExchange;
    @Value("${rabbitmq.queue.product-queue}")
    private String productQueueName;
    @Value("${rabbitmq.queue.payment-queue}")
    private String paymentQueueName;

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(fanoutExchange)
                .durable(true)
                .build();
    }

    @Bean
    public Queue productQueue() {
        return new Queue(productQueueName, true);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(paymentQueueName, true);
    }

    @Bean
    public Binding productBinding(Queue productQueue) {
        return BindingBuilder.bind(productQueue)
                .to(fanoutExchange());
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
