package com.fintechedge.payflex.service;

import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;


    public User getUserById(Integer id) {

        return userRepository.findById(id).get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User user) {

        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setStaffID(user.getStaffID());
            updatedUser.setEmailAddress(user.getEmailAddress());
            updatedUser.setEmployer(user.getEmployer());
            updatedUser.setMobileNumber(user.getMobileNumber());
            updatedUser.setAccountNumber(user.getAccountNumber());

            return userRepository.save(updatedUser);
        } else {
            return null;
        }

    }

    public void deleteUser(Integer id) {

        Optional<User> existingUser = userRepository.findById(id);
        existingUser.ifPresent(user -> user.setEnabled(false));

        userRepository.save(existingUser.get());

    }


}


//    public Mono<User> updateUser(UUID id, User user) {
//        return userRepository.findById(id)
//                .flatMap(existingUser -> {
//                    existingUser.setFirstName(user.getFirstName());
//                    existingUser.setLastName(user.getLastName());
//                    existingUser.setStaffID(user.getStaffID());
//                    existingUser.setEmailAddress(user.getEmailAddress());
//                    existingUser.setEmployer(user.getEmployer());
//                    existingUser.setMobileNumber(user.getMobileNumber());
//                    existingUser.setAccountNumber(user.getAccountNumber());
//
//                    return userRepository.save(existingUser);
//                }
//        );