package com.fintechedge.payflex.service;

import com.fintechedge.payflex.dto.PaymentRequest;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentIntegrationService {
    private final WebClient webClient;

    public PaymentIntegrationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ApiResponse> callExternalApi(PaymentRequest paymentRequest) {
        return webClient.get()
                .uri("https://api.korba.com/v1/payment")
                .retrieve()
                .bodyToMono(ApiResponse.class);
    }

}
