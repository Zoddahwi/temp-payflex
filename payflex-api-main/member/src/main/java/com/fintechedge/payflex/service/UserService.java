package com.fintechedge.payflex.service;

import com.fintechedge.payflex.dto.UserDTO;
import com.fintechedge.payflex.model.User;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


public interface UserService {
    Mono<User> findById(UUID id);
//
    Flux<User> findAll();

    Mono<User> save(UserDTO user);
//    Mono<Void> deleteById(Integer id);

    Mono<Void> deleteById(UUID id);

//    Mono<Void> deleteBystaffId(String staffId);

    Mono<Object> updateUser(UUID id, User user);


    Flux<User> addBulkUsers(MultipartFile file);



}
