//package com.fintechedge.payflex.service.otp;
//
//import com.fintechedge.kafka.model.EmailEvent;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EmailEventProducer {
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final String emailTopic = "otp-topic";
//
//    public EmailEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendEmailEvent(EmailEvent emailEvent) {
//        kafkaTemplate.send(emailTopic, emailEvent.getEmail(), emailEvent.toString());
//    }
//}
//
