package com.fintechedge.userservice.repository;

import com.fintechedge.userservice.model.VerificationToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface VerificationTokenRepository extends ReactiveCrudRepository<VerificationToken, Long> {
    Mono<VerificationToken> findByToken(String token);
}
