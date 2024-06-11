package com.fintechedge.userservice.user;

import com.fintechedge.userservice.model.PasswordResetToken;
import com.fintechedge.userservice.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends R2dbcRepository<User, UUID> {

    Mono<User> findByEmailOrUsername(String email , String username);

    Mono<User> findByUsername(String username);

    Mono<User> findByPhoneNumber(String phoneNumber);

    Mono <PasswordResetToken> findByEmail(String email);

//    PasswordResetToken findByEmail(String email);

//    WebFilter findByUsername(String username);


//    Mono<User> findByTokenAndEmail (Mono<String> token, Mono<String> email);
}
