package com.fintechedge.userservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender javaMailSender;


//    public EmailService(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }

//    @Async
//public CompletableFuture<String> sendEmail(String to, String subject, String body) {
//    SimpleMailMessage message = new SimpleMailMessage();
//    message.setTo(to);
//    message.setSubject(subject);
//    message.setText(body);
//
//    javaMailSender.send(message);
//    return CompletableFuture.completedFuture("Email sent successfully");
//}


    @Async
    public Mono<String> sendEmail(String to, String subject, String body) {
        if (to == null || to.isEmpty() || subject == null || subject.isEmpty() || body == null || body.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Email details cannot be null or empty"));
        }

        if (!EmailValidator.getInstance().isValid(to)) {
            return Mono.error(new IllegalArgumentException("Invalid email address: " + to));
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
        return Mono.just("Email sent successfully");
    }
}
