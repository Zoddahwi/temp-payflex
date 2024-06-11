package com.fintechedge.userservice.token;

import com.fintechedge.userservice.model.Token;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TokenRepository extends ReactiveCrudRepository<Token, String> {

    Mono<Token>findByEmail(String email);
}
