package com.fintechedge.userservice.auth.controller;


import com.fintechedge.userservice.auth.service.AuthService;
import com.fintechedge.userservice.auth.ProfileUpdateRequest;
import com.fintechedge.userservice.auth.service.OtpService;
import com.fintechedge.userservice.auth.service.OtpServiceImpl;
import com.fintechedge.userservice.auth.service.SmsService;
import com.fintechedge.userservice.dto.AuthResponse;
import com.fintechedge.userservice.dto.LoginRequest;
import com.fintechedge.userservice.dto.RegisterRequest;
import com.fintechedge.userservice.model.User;
import com.fintechedge.userservice.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    private final OtpService otpServiceImp;


    private final UserRepository userRepository;

    private final SmsService smsService;

    @Autowired
    public AuthController(AuthService authService, OtpService otpServiceImp, UserRepository userRepository, SmsService smsService) {
        this.authService = authService;
        this.otpServiceImp = otpServiceImp;
        this.userRepository = userRepository;
        this.smsService = smsService;
    }

    @PostMapping("/register")
    public ResponseEntity<Mono<AuthResponse>> register(
            @RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok().body(authService.register(request));
    }


    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody LoginRequest request) {
//        smsService.sendSmsVerificationCode(request.getPhoneNumber());
        return authService.login(request);
    }

    @PostMapping("/new_login")
    public Mono<ResponseEntity<String>> login(@RequestParam String email) {
        return otpServiceImp.generateOtpAndSend(email)
                .map(tempPassword -> ResponseEntity.ok("A temporary password has been sent to your email."))
                .onErrorResume(e -> {
                    if (e instanceof IllegalArgumentException) {
                        return Mono.just(ResponseEntity.badRequest().body("Invalid email address."));
                    } else if (e instanceof MessagingException) {
                        return Mono.just(ResponseEntity.status(500).body("An error occurred while sending the email."));
                    } else {
                        return Mono.just(ResponseEntity.status(500).body("An unexpected error occurred."));
                    }
                });
    }


    @GetMapping("/verify")
    public Mono<String> verifyUser(@RequestParam("id") UUID id, @RequestParam("token") String token) {
        return authService.validateVerificationToken(id, token)
                .map(result -> result == null ? "Email verified successfully" : "Error verifying email: " + result);
    }

    @PostMapping("/changePassword")
    public Mono<String> changePassword(@RequestParam("id") UUID id, @RequestParam("token") String token, @RequestParam("password") String newPassword) {
        String result = String.valueOf(authService.validatePasswordResetToken(id, token));
        if (result == null) {
            return userRepository.findById(id)
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid user Id:" + id)))
                    .flatMap(user -> authService.changeUserPassword(user, newPassword)
                            .thenReturn("Password changed successfully"));
        } else {
            return Mono.just("Error changing password: " + result);
        }
    }


    @PutMapping("/updateProfile/{id}")
    public Mono<ResponseEntity<User>> updateProfile(@PathVariable UUID id, @RequestBody ProfileUpdateRequest request) {
        return authService.updateProfile(id, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


//        return authService.updateProfile(id, request)
//                .map(user -> {
//                    AuthResponse response = new AuthResponse();
//                    response.setMessage("Profile updated successfully");
//                    return response;
//                });


    @PostMapping("/initiatePasswordReset")
    public Mono<String> initiatePasswordReset(@RequestParam String email, String username) {
        return authService.initiatePasswordReset(email, username);
    }

//    @PostMapping("/resetPassword")
//    public Mono<String> resetPassword(String email, String password, String token) {
//
//        return authService.resetPassword(email, password, token);
//    }

    @PostMapping("/send-sms-verification")
    public void sendSmsVerification(@RequestParam String phoneNumber) {

        smsService.sendSmsVerificationCode(phoneNumber);

//        authService.sendSmsVerificationCode(phoneNumber);
    }


}
