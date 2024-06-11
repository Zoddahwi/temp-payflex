package com.fintechedge.payflex;

import com.fintechedge.payflex.controller.UserController;
import com.fintechedge.payflex.dto.UserDTO;
import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.repository.UserRepository;
import com.fintechedge.payflex.service.UserService;
import com.fintechedge.payflex.service.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;


@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    @Autowired
    private UserService userService;

    private UserRepository userRepository;



    @Test
    public void testCreateUser(){

        UserDTO newUser = new UserDTO("Nana","hfsf","Afia", "1","afia@gmail.com","Payflex","08012345678", "1234567890","GTB", "100000", "MTN");
        User savedUser = new User("Nana","Afia", "1","afia@gmail.com","Payflex","08012345678", "1234567890","GTB", "100000", "MTN");
        when(userService.save(newUser)).thenReturn(Mono.just(savedUser));

        webTestClient.post()
                .uri("/api/v1/user")   //build the request
                .contentType(MediaType.APPLICATION_JSON)    //set the request header
                .bodyValue(newUser)    //set the request body
                .exchange()     //perform the request
                .expectStatus().isCreated()      //check that the HTTP status is 200
                .expectBody(User.class)         //check that the response body is not empty
                .isEqualTo(savedUser);

        verify(userService, times(1)).save(newUser);

    }


    @Test
    public void testSaveUser() {
        UserDTO newUser = new UserDTO();
        newUser.setFirstName("Nana");
        newUser.setLastName("Afia");
        newUser.setStaffId("1");
        newUser.setEmailAddress("afia@gmail.com");
        newUser.setEmployer("Payflex");
        newUser.setMobileNumber("08012345678");
        newUser.setAccountNumber("1234567890");
        newUser.setDestinationBank("GTB");
        newUser.setSalary("100000");
        newUser.setDestinationTelco("MTN");

        UserDTO savedUser = new UserDTO();
        savedUser.setFirstName("Nana");
        savedUser.setLastName("Afia");
        savedUser.setStaffId("1");

    }

    @Test
    public void testGetUserById(){
        UUID id = UUID.randomUUID();
        User mockUser = new User(id, "Nana","Afia", "1","afia@gmail.com","Payflex","08012345678", "1234567890","GTB", "100000", "MTN");

        when(userService.findById(id)).thenReturn(Mono.just(mockUser));

        webTestClient.get()
                .uri("/api/v1/user/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .isEqualTo(mockUser);

        verify(userService, times(1)).findById(id);
    }
//    @Test
//    public void testGetAllUsers(){
//
//
//    }

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

    }

//    @Test
//    public void findAll_shouldReturnAllUsers() {
//
//        List<User> mockUsers = Arrays.asList(new User(), new User());
//        when(userService.findAll()).thenReturn(Flux.fromIterable(mockUsers));
//
//        Flux<User> response = userController.findAll();
//
//        StepVerifier.create(response)
//                .expectNextCount(2)
//                .verifyComplete();
//
//
//    }

    //given




//    @Test
//    public void testGetAllUsers(){
//        when(userService.findAll())
//                .thenReturn(Flux
//                        .just(new User("Nana","Afia", "1","afia@gmail.com","Payflex","08012345678", "1234567890","GTB", "100000", "MTN")));
//
//        webTestClient.get()
//                .uri("/api/v1/user")
//                .exchange() //perform the request
//                .expectStatus().isOk()  //check that the HTTP status is 200
//                .expectHeader().contentType("application/json") //check that the content type return is JSON
//                .expectBodyList(User.class)
//                .hasSize(2)
//                .contains(new User("Nana","Afia", "1","afia@gmail.com","Payflex","08012345678", "1234567890","GTB", "100000", "MTN"));
//
////        //verify that the findAll method of the userService is called
////        verify(userService, times(1)).findAll();
//
//        //verify that the findAll method of the userService is called
//        verify(userService).findAll();//check that the number of users returned is 2
//    }
}
