package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.Telco;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TelcoService {

    Mono<Telco> findById(UUID id);

    Mono<Telco> save(Telco telco);

    Mono<Void> deleteById(UUID id);

    Mono<Object> updateTelco(UUID id, Telco telco);

    Flux<Telco> findAll();
}
