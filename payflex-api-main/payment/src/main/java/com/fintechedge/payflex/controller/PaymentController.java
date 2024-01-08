package com.fintechedge.payflex.controller;

import com.fintechedge.payflex.dto.PaymentRequest;
import com.fintechedge.payflex.dto.PaymentResponse;
import com.fintechedge.payflex.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PaymentController {

    private final PaymentService paymentService;
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/request-advance-salary")
    public Mono<PaymentResponse> requestSalary(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.requestSalary(new PaymentRequest(paymentRequest.getAccountNumber(),
                paymentRequest.getDestinationBank(), paymentRequest.getAmount(),
                paymentRequest.getDestinationTelco(), paymentRequest.getMobileNumber(),
                paymentRequest.getTransactionRef(), paymentRequest.getTransactionId(),
                paymentRequest.getUserId(), paymentRequest.getStatus()));
    }
}
