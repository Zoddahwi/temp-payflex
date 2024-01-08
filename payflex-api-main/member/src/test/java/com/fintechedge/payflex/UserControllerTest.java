package com.fintechedge.payflex;

import com.fintechedge.payflex.controller.UserController;
import com.fintechedge.payflex.dto.UserDTO;
import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;
    private String userId;

    @Test
    public void getUserById() {
        String userId = "1";
        User mockUser = new User(UUID.randomUUID(), userId, "wise"," zoddah" ,"zodahwise@gmail.com");
        given(userService.findById(UUID.fromString(userId))).willReturn(Mono.just(mockUser));

        webTestClient.get()
                .uri("/user/{id}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(mockUser);

    }

//    @Test
//    public void createUser_ShouldReturnCreatedUser(){
//        UserDTO newUser = new UserDTO(UUID.randomUUID(), userId, "wise"," zoddah" ,"zodahwise@gmail.com");
//        given(userService.save(newUser)).willReturn(Mono.just(newUser));
//    }
}
