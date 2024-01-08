package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.DestinationBank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DestinationBankService {
    Mono<DestinationBank> save(DestinationBank destinationBank);

    Mono<DestinationBank> findById(UUID id);

    Mono<Void> deleteById(UUID id);

    Mono<Object> updateDestinationBank(UUID id, DestinationBank destinationBank);

    Flux<DestinationBank> findAll();
}
