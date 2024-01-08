package com.fintechedge.payflex.service;

import com.fintechedge.payflex.dto.PaymentRequest;
import com.fintechedge.payflex.dto.PaymentResponse;
import com.fintechedge.payflex.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {
    private final WebClient webClient;
    private final PaymentRepository paymentRepository;

    public PaymentService(@Value("${payment.service.url}") String apiUrl, PaymentRepository paymentRepository) {
        this.webClient = WebClient.create(apiUrl);
        this.paymentRepository = paymentRepository;
    }

    public Mono<PaymentResponse> requestSalary(PaymentRequest paymentRequest) {

//        PaymentResponse paymentResponse1 = new PaymentResponse();
//        paymentResponse1.setStatus("SUCCESS");
//        paymentResponse1.setMessage("Salary request successful");
//        return Mono.just(paymentResponse1);


        if (isPaymentRequestValid(paymentRequest)) {
            return webClient.post()
                    .uri("https://api.korba.com/v1/payment")
                    .body(Mono.just(paymentRequest), PaymentRequest.class)
                    .retrieve()
                    .bodyToMono(PaymentResponse.class)
                    .flatMap(paymentResponse -> {
                        PaymentRequest paymentRequest1 = new PaymentRequest();
                        paymentRequest1.setAccountNumber(paymentRequest.getAccountNumber());
                        paymentRequest1.setAmount(paymentRequest.getAmount());
                        paymentRequest1.setDestinationBank(paymentRequest.getDestinationBank());
                        paymentRequest1.setDestinationTelco(paymentRequest.getDestinationTelco());
                        paymentRequest1.setMobileNumber(paymentRequest.getMobileNumber());
                        paymentRequest1.setTransactionRef(paymentRequest.getTransactionRef());
                        paymentRequest1.setTransactionId(paymentRequest.getTransactionId());
                        paymentRequest1.setUserId(paymentRequest.getUserId());
                        paymentRequest1.setStatus(paymentResponse.getStatus());

                        return paymentRepository.save(paymentRequest1)
                                .thenReturn(paymentResponse);
                    })
                    .onErrorResume(error -> {
                        PaymentResponse paymentResponse = new PaymentResponse();
                        paymentResponse.setStatus("FAILED");
                        paymentResponse.setMessage(error.getMessage());
                        return Mono.just(paymentResponse);
                    })
                    ;

        } else {
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setStatus("FAILED");
            paymentResponse.setMessage("Invalid payment request");
            return Mono.just(paymentResponse);
        }

    }

    private boolean isPaymentRequestValid(PaymentRequest paymentRequest) {
        return paymentRequest != null
                && paymentRequest.getAccountNumber() != null
                && paymentRequest.getAmount() != null
                && paymentRequest.getDestinationBank() != null
                && paymentRequest.getDestinationTelco() != null
                && paymentRequest.getMobileNumber() != null
                && paymentRequest.getTransactionRef() != null
                && paymentRequest.getTransactionId() != null
                && paymentRequest.getUserId() != null;
    }

}


