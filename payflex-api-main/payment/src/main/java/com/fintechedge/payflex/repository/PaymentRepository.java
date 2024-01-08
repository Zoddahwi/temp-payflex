package com.fintechedge.payflex.repository;

import com.fintechedge.payflex.dto.PaymentRequest;
import com.fintechedge.payflex.dto.PaymentResponse;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface PaymentRepository extends ReactiveMongoRepository <PaymentRequest, String> {

   Mono<PaymentResponse> findByTransactionRef(String transactionRef);
   Mono<PaymentResponse> findByTransactionId(String transactionId);
}
