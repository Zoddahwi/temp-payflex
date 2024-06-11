package com.fintechedge.kafka.service.email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.IOException;
import java.util.Properties;

public class EmailSender {

    public static void main(String[] args) throws MessagingException, IOException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port" , "smtp.gmail.com");

        Session session = Session.getInstance(properties);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("your-email@example.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("recipient@example.com"));
        message.setSubject("Test Email");

        // Create a MIME body part for the message content
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Please Verify Your Email Address");

//        BodyPart attachmentBodyPart = new MimeBodyPart();
//        ((MimeBodyPart) attachmentBodyPart).attachFile("C:\\Users\\user\\Desktop\\test.txt");

        // Create a multipart message with the message content and attachment
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
//        multipart.addBodyPart(attachmentBodyPart);

        // Set the multipart content to the message
        message.setContent(multipart);

        // Send the email
        Transport.send(message);


        System.out.println("Email Sent successfully!");
    }
}
