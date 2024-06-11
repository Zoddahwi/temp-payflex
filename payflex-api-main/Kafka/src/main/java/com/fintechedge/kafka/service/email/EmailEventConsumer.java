package com.fintechedge.kafka.service.email;

import com.fintechedge.kafka.model.EmailEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "email-events", groupId = "payflexId")
public class EmailEventConsumer {

    @Autowired
    private EmailService emailService;

    @KafkaHandler
    public void handleEmailEvent(EmailEvent senderEmailEvent){
        emailService.sendEmail(senderEmailEvent.getEmail(), senderEmailEvent.getSubject(), senderEmailEvent.getBody());
    }

}
