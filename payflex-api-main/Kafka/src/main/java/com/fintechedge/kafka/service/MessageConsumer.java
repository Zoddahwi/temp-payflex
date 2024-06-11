package com.fintechedge.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    @KafkaListener(topics = "otp-topic", groupId = "payflexId")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }


}
