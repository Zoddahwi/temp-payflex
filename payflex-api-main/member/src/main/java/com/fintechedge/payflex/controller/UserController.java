package com.fintechedge.payflex.controller;

import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return new ResponseEntity<User>
                (userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>
                (userService.getAllUsers(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>
                (userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return new ResponseEntity<User>
                (userService.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}



//    @PostMapping(path="/user", consumes= MediaType.APPLICATION_JSON_VALUE,
//            produces=MediaType.APPLICATION_JSON_VALUE)
//    public Mono<User> createUser(@RequestBody User user) {
//        return createWebClient.post()
//                .uri("/user")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(BodyInserters.fromValue(user))
//                .retrieve()
//                .bodyToMono(User.class);
//    }

//    @PutMapping(path="/user/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
//    public Mono<User> createPost(@RequestBody User user){
//    return createWebClient.user()
//            .uri("/user")
//            .retrieve()
//            .bobyToMono(User.class);
//
//    }
//
//    @DeleteMapping(path="/user")
//    public Mono<User> deleteUser(@PathVariable String id) {
//    return createWebClient.delete()
//            .uri("/user")
//            .retrive()
//            .bodyToMono(User.class);
//    }


