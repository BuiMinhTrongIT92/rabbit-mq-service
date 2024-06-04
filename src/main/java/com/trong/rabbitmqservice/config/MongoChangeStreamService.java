package com.trong.rabbitmqservice.config;

import com.mongodb.client.model.changestream.FullDocument;
import com.trong.rabbitmqservice.publisher.RabbitProducer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoChangeStreamService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RabbitProducer rabbitProducer;

    @PostConstruct
    public void init() {
        try {
            mongoTemplate.getCollection("products")
                    .watch()
                    .fullDocument(FullDocument.UPDATE_LOOKUP)
                    .forEach(event -> {
                        rabbitProducer.sendMessage(event.getFullDocument());
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize MongoChangeStreamService", e);
        }
    }
}