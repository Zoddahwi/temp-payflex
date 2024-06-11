package com.fintechedge.payflex.service.otp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${infobip.api.key}")
    private String apiKey;

    @Value("${infobip.api.base.url}")
    private String baseUrl;


    private final WebClient webClient;

    public void sendSms(String mobileNumber, String message, String s) {
        String smsApiUrl = baseUrl + "/sms/2/text/advanced";


        Map<String, Object> msg = new HashMap<>();
        msg.put("from", "InfoSMS");
        msg.put("text", message);

        Map<String, String> destination = new HashMap<>();
        destination.put("to", mobileNumber);

        msg.put("destinations", destination);

        Map<String, Object> body = new HashMap<>();
        body.put("messages", msg);

        webClient.post()
                .uri(smsApiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "App " + apiKey)
                .body(Mono.just(body), Map.class)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("SMS sent successfully"),
                        error -> System.err.println("Failed to send SMS. Exception: " + error.getMessage()));
    }

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