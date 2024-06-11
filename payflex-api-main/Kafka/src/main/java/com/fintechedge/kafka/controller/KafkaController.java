package com.fintechedge.kafka.controller;

import com.fintechedge.kafka.service.MessageConsumer;
import com.fintechedge.kafka.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/publish")
    public String sendMessage(String topic, String message){
        messageProducer.sendMessage(topic, message);
        return "Message sent to the Kafka Topic java_in_use_topic Successfully";
    }
}
