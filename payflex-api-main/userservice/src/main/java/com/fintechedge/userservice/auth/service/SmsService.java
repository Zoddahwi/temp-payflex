package com.fintechedge.userservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${infobip.api.key}")
    private String apiKey;

    @Value("${infobip.api.base.url}")
    private String baseUrl;


    private final RestTemplate restTemplate;

    public void sendSms(@NotNull String phoneNumber, String message, String s) {
        String smsApiUrl = baseUrl + "/sms/2/text/advanced";

//        String correctPrefix = "+233";
//        if (!phoneNumber.startsWith(correctPrefix)) {
//            phoneNumber = correctPrefix + phoneNumber;
//        }

//        if (!phoneNumber.startsWith("+")) {
//            phoneNumber = "+233" + phoneNumber;
//        }

//        if (phoneNumber == null || !phoneNumber.matches("\\+?[0-9]+")) {
//            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
//        }




        HttpHeaders headers = createHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> msg = new HashMap<>();
        msg.put("from", "InfoSMS");
        msg.put("text", message);

        Map<String, String> destination = new HashMap<>();
        String correctPrefix = "+233";
        if (!phoneNumber.startsWith(correctPrefix)) {
            phoneNumber = correctPrefix + phoneNumber;
        }
        destination.put("to", phoneNumber);

//        Map<String, String> destination = new HashMap<>();
//        destination.put("to", phoneNumber);

        msg.put("destinations", Collections.singletonList(destination));

        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(msg);

        Map<String, Object> body = new HashMap<>();
        body.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    smsApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                // Handle success
                System.out.println("SMS sent successfully");
            } else {
                // Handle failure
                System.err.println("Failed to send SMS. Status code: " + responseEntity.getStatusCode());
            }
        } catch (RestClientException e) {
            // Handle exception
            System.err.println("Failed to send SMS. Exception: " + e.getMessage());
            // You can add more specific error handling here based on the exception type or message
        } catch (Exception e) {
            // This will catch any other exceptions that might occur
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

//    private String getClass(String phoneNumber) {
//        return phoneNumber.substring(1);
//    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "App " + apiKey);
        return headers;
    }

    public void sendSmsVerificationCode(String phoneNumber) {
        Random random = new Random();
        int code = random.nextInt(999999);
        String message = "Your verification code is: " + code;
        sendSms(phoneNumber, message, "verification");
    }
}