package com.fintechedge.payflex.service;

import com.fintechedge.payflex.dto.UserDTO;
import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.model.setup.Account;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserService {
    Mono<User> findById(UUID id);
//
    Flux<User> findAll();

    Mono<User> save(UserDTO user);
//    Mono<Void> deleteById(Integer id);

    Mono<Void> deleteById(UUID id);

//    Mono<Void> deleteBystaffId(String staffId);

    Mono<Object> updateUser(UUID id, User user);


    Mono<User> addBulkUsers(MultipartFile file);



    Mono<Boolean> verifyOtp(String userId, String otp);

    Mono<String> generateOtpAndSend(UserDTO user);

    Mono<User> registerUser(UserDTO user);

    Mono<User> readUsersFromExcelFile(MultipartFile file);

//    Mono<User> addAccountToUser(UUID userId, Account accountId);
}
