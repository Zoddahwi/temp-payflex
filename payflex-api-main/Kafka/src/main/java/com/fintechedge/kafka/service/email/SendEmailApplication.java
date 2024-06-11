package com.fintechedge.kafka.service.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SendEmailApplication {

    private EmailService emailService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SendEmailApplication.class, args);
        SendEmailApplication app = context.getBean(SendEmailApplication.class);

        app.Run();
    }

    private void Run() {
        emailService.sendEmail("zodahwise@gmail.com", "Payflex", "Testing the email service");
    }

}
