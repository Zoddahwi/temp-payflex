package com.fintechedge.payflex.controller;

import com.fintechedge.payflex.dto.PaymentRequest;
import com.fintechedge.payflex.service.PaymentIntegrationService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PaymentIntegrationController {
    private final PaymentIntegrationService paymentIntegrationService;


    public PaymentIntegrationController(PaymentIntegrationService paymentIntegrationService) {
        this.paymentIntegrationService = paymentIntegrationService;
    }

    @PostMapping("/call-external-api")
    public Mono<ApiResponse> callExternalApi(@RequestBody PaymentRequest paymentRequest) {
        return paymentIntegrationService.callExternalApi(paymentRequest);
    }
}
