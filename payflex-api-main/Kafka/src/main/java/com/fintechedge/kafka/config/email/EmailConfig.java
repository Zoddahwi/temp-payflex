package com.fintechedge.kafka.config.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("live.smtp.mailtrap.io");
        mailSender.setPort(587);
        mailSender.setUsername("api");
        mailSender.setPassword("cd689102fc911b00ca8775b88ddd34fc");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");

        return mailSender;
    }



    @Bean
    public MimeMessage mimeMessage(JavaMailSender javaMailSender) {
        return javaMailSender.createMimeMessage();
    }

    @Bean
    public MimeMessageHelper mimeMessageHelper(MimeMessage mimeMessage) {
        return new MimeMessageHelper(mimeMessage);
    }
}
