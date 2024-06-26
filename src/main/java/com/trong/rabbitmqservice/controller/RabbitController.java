package com.trong.rabbitmqservice.controller;

import com.trong.rabbitmqservice.publisher.RabbitProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {
    @Autowired
    private RabbitProducer rabbitProducer;

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message) {
        rabbitProducer.sendMessage(message);
        return new ResponseEntity<>(String.format("Message send %s", message), HttpStatus.OK);
    }
}
