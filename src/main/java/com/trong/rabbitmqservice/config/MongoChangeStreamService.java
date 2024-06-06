package com.trong.rabbitmqservice.config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.changestream.FullDocument;
import com.trong.rabbitmqservice.publisher.RabbitProducer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class MongoChangeStreamService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private RabbitProducer rabbitProducer;

    @PostConstruct
    public void init() {
        List<MongoCollection<Document>> collections = Arrays.asList(mongoTemplate.getCollection("products"),
                mongoTemplate.getCollection("payments"));
        try {
            collections.forEach(collection -> {
                collection
                        .watch()
                        .fullDocument(FullDocument.UPDATE_LOOKUP)
                        .forEach(event -> {
                            rabbitProducer.sendMessage(event.getFullDocument());
                        });
            });
        } catch (Exception e) {
            log.error("Failed to initialize MongoChangeStreamService", e);
            throw new RuntimeException("Failed to initialize MongoChangeStreamService", e);
        }
    }
}