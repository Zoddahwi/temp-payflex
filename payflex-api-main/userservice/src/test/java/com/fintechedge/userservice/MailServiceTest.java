package com.fintechedge.userservice;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    private JavaMailSenderImpl mailSender;

    private GreenMail testSmtp;

    @BeforeEach
    public void testSmtpInit(){
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
        mailSender.setPort(ServerSetupTest.SMTP.getPort());
        mailSender.setHost("localhost");
    }

    @Test
    public void testEmail() throws MessagingException, IOException {
        String subject = "Test subject";
        String body = "Test body";

        mailSender.send(mimeMessage -> {
            mimeMessage.setSubject(subject);
            mimeMessage.setText(body);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("test@recipient.com"));
        });

        MimeMessage[] receivedMessages = testSmtp.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        assertEquals(subject, receivedMessages[0].getSubject());
        assertEquals(body, receivedMessages[0].getContent());
    }

    @AfterEach
    public void cleanup(){
        testSmtp.stop();
    }
}
