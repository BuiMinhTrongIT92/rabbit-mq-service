package com.trong.rabbitmqservice.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitProducer {
    @Value("${rabbitmq.exchange.fanout-exchange}")
    private String fanoutExchange;
    private RabbitTemplate rabbitTemplate;

    private RabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //Send message from Producer
    public void sendMessage(Object message) {
        log.info("Send message from Producer: " + message);
        rabbitTemplate.convertAndSend(fanoutExchange, "", message);
    }
}
