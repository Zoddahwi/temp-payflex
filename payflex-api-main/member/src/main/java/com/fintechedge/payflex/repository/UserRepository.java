package com.fintechedge.payflex.repository;

import com.fintechedge.payflex.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> save(Boolean user);
}


