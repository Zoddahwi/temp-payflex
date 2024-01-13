package com.fintechedge.userservice.service;

import com.fintechedge.userservice.dto.NewUserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface KeyCloakService {
    Mono<NewUserDTO> save(NewUserDTO user);

    Mono<NewUserDTO> findByUsername(String username);

    Flux<NewUserDTO> findAll();

    Mono<Object> updateUser(UUID id, NewUserDTO user);

    Mono<Void> deleteById(UUID id);

    Mono<Void> sendVerificationLink(UUID id);

    Mono<Void> sendPasswordResetLink(UUID id);

    Mono<Void> getKeycloakInstance();



}
