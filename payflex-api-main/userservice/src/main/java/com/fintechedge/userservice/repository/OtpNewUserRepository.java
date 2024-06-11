package com.fintechedge.userservice.repository;

import com.fintechedge.userservice.model.OtpNewUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OtpNewUserRepository extends ReactiveCrudRepository<OtpNewUser, UUID> {
}
