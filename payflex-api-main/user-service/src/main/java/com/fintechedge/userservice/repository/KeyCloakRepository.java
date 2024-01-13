package com.fintechedge.userservice.repository;

import com.fintechedge.userservice.dto.NewUserDTO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KeyCloakRepository extends ReactiveCrudRepository<NewUserDTO, UUID> {
}
