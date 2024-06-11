package com.fintechedge.payflex.controller;

import com.fintechedge.payflex.dto.UserDTO;
import com.fintechedge.payflex.model.setup.Account;
import com.fintechedge.payflex.service.UserService;
import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.service.otp.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final SmsService smsService;


    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Autowired
    public UserController(UserService userService, SmsService smsService) {
        this.userService = userService;
        this.smsService = smsService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user by ID", description = "Returns a single user")
    Mono<ResponseEntity<User>> findById(@PathVariable UUID id) {
        return userService.findById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Find all users", description = "Returns a list of users")
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a user", description = "Creates a new user")
    Mono<ResponseEntity<User>> save(@RequestBody UserDTO user) {
        return userService.save(user)
                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(savedUser));
    }

//    @PostMapping("/{userId}/account")
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Add an account to a user", description = "Adds an account to a user")
//    public Mono<User> addAccountToUser(@PathVariable UUID userId, @RequestBody Account account) {
//        return userService.addAccountToUser(userId, account);
//    }


    @PostMapping("/generate-otp")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Generate OTP", description = "Generates OTP")
    Mono<String> generateOtpAndSend(@RequestBody UserDTO user) {
        return userService.generateOtpAndSend(user);
    }

    @PostMapping("/verify-otp")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Verify OTP", description = "Verifies OTP")
    Mono<Boolean> verifyOtp(@RequestParam String userId, @RequestParam String otp) {
        return userService.verifyOtp(userId, otp);
    }

    @PostMapping("/registerUser")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register User", description = "Registers User")
    Mono<User> registerUser(@RequestBody UserDTO user) {
        return userService.registerUser(user);
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
    public Mono<User> addBulkUsers(@RequestParam("file") MultipartFile file) {
        return userService.readUsersFromExcelFile(file)
                .flatMap(user -> userService.save(new UserDTO(user)))
                .onErrorResume(e -> {
                    LOGGER.log(Level.SEVERE, "Error occurred", e);
                    return Mono.error(e);
                });
    }


//    @PostMapping("/bulk")
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(summary = "Add bulk users", description = "Adds bulk users")
//    public Mono<User> addBulkUsers(@RequestParam("file") MultipartFile file) {
//        return userService.readUsersFromExcelFile(file)
//                .flatMap(user -> userService.save(new UserDTO(user)))
//                .next()
//                .onErrorResume(e -> {
//                    e.printStackTrace();
//                    return Mono.error(e);
//                });
//    }

}




