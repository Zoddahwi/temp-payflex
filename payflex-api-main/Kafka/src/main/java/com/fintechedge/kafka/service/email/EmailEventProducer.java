package com.fintechedge.kafka.service.email;

import com.fintechedge.kafka.model.EmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailEventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEmailEvent(EmailEvent emailEvent) {
        kafkaTemplate.send("email-events", emailEvent.getEmail(), emailEvent.toString());
    }




}
