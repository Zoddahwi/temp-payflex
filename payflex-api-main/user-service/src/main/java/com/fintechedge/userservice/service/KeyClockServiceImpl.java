package com.fintechedge.userservice.service;

import com.fintechedge.userservice.dto.NewUserDTO;
import com.fintechedge.userservice.repository.KeyCloakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class KeyClockServiceImpl implements KeyCloakService {
    private final KeyCloakRepository keycloakRepository;

    @Autowired
    public KeyClockServiceImpl(KeyCloakRepository keycloakRepository) {
        this.keycloakRepository = keycloakRepository;
    }

    @Override
    public Mono<NewUserDTO> save(NewUserDTO user) {

        NewUserDTO newUserDTO = NewUserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .selectCompany(user.getSelectCompany())
                .staffId(user.getStaffId())
                .email(user.getEmail())
                .build();

        Mono<NewUserDTO> newUsers = keycloakRepository.save(newUserDTO);
        return newUsers;
    }

    @Override
    public Mono<NewUserDTO> findByUsername(String username) {
        return keycloakRepository.findById(UUID.fromString(username));
    }

    @Override
    public Flux<NewUserDTO> findAll() {
        return keycloakRepository.findAll();
    }

    @Override
    public Mono<Object> updateUser(UUID id, NewUserDTO user) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return null;
    }

    @Override
    public Mono<Void> sendVerificationLink(UUID id) {
        return null;
    }

    @Override
    public Mono<Void> sendPasswordResetLink(UUID id) {
        return null;
    }

    @Override
    public Mono<Void> getKeycloakInstance() {
        return null;
    }
}
