package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.Employer;
import io.r2dbc.spi.Result;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployerService {
    Mono<Employer> findById(UUID id);

   Mono<Employer> save(Employer employer);

   Flux<Employer> findAll();

   Mono<Void> deleteById(UUID id);

   Mono<Object> updateEmployer(UUID id, Employer employer);

}
