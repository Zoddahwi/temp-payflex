package com.fintechedge.payflex.repository;

import com.fintechedge.payflex.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> findBystaffId(String staffId);
    Mono<User> findByEmailAddress(String emailAddress);
    Mono<User> findByMobileNumber(String mobileNumber);
    Mono<User> findByAccountNumber(String accountNumber);
    Mono<User> findByEmployer(String employer);
    Mono<User> findByFirstName(String firstName);
    Mono<User> findByLastName(String lastName);
    Mono<User> findByFirstNameAndLastName(String firstName, String lastName);
    Mono<Void> deleteBystaffId(String staffId);



}
