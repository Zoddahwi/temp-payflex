package com.fintechedge.payflex.controller;

import com.fintechedge.payflex.dto.UserDTO;
import com.fintechedge.payflex.service.UserService;
import com.fintechedge.payflex.model.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private  final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @SneakyThrows
//    @GetMapping("/id")
//    @Operation(summary = "Find user by ID", description = "Returns a single user")
//    Mono<ResponseEntity<User>> findById(@PathVariable UUID id){
//        return userService.findById(id)
//                .map(ResponseEntity::ok)
//                .defaultIfEmpty(ResponseEntity.notFound().build());
//    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user by ID", description = "Returns a single user")
    Mono<ResponseEntity<User>> findById(@PathVariable UUID id){
        return userService.findById(id)
                .map(ResponseEntity::ok);
    }


    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Find all users", description = "Returns a list of users")
    Flux<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a user", description = "Creates a new user")
    Mono<ResponseEntity<User>> save(@RequestBody UserDTO user) {
        return userService.save(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Update a user", description = "Updates an existing user")
    Mono<ResponseEntity<User>> update(@PathVariable UUID id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(updatedUser -> ResponseEntity.status(HttpStatus.CREATED).body((User) updatedUser));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a user", description = "Deletes an existing user")
    Mono<Void> delete(@PathVariable UUID id) {
        return userService.deleteById(id);

    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add bulk users", description = "Adds bulk users")
    public Flux<User> addBulkUsers(@RequestParam("file")MultipartFile file){
        return userService.addBulkUsers(file);
    }


}




