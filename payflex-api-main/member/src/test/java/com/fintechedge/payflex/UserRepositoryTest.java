package com.fintechedge.payflex;

import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.util.UUID;

@DataR2dbcTest
@EnableR2dbcRepositories(basePackages = "com.fintechedge.payflex.repository")
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveSingleUser() {
        User user = User.builder()
                .id(UUID.fromString("cf36f4bb-98ac-45f7-9494-0c3eead8a9a5"))
                .firstName("John")
                .lastName("Doe")
                .staffId("123456")
                .accountNumber("1234567890")
                .emailAddress("afia@gmail.com")
                .employer("Payflex")
                .mobileNumber("08012345678")
                .destinationBank("GTB")
                .destinationTelco("MTN")
                .salary("100000")
                .build();

        Publisher<User> setup = userRepository.deleteAll().thenMany(userRepository.save(user));
        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();


    }
}
