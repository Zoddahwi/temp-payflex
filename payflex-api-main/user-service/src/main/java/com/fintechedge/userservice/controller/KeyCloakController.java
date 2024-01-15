package com.fintechedge.userservice.controller;

import com.fintechedge.userservice.dto.NewUserDTO;
import com.fintechedge.userservice.service.KeyClockService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/new-user")
public class KeyCloakController {
    @Autowired
    KeyClockService service;

    @PostMapping
    public String save(@RequestBody NewUserDTO newUser) {
        service.save(newUser);
        return "User Added Successfully.";
    }

    @GetMapping("/{userName}")
    public List<UserRepresentation> getUser(@PathVariable String userName){
        List<UserRepresentation> user = service.getUser(userName);
        return user;
    }

    @PutMapping("/update/{userId}")
    public String updateUser(@PathVariable String userId, NewUserDTO newUser){
        service.updateUser(userId, newUser);
        return "User Details Updated Successfully.";
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable String userId){
        service.deleteUser(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping("/verification-link/{userId}")
    public String sendVerificationLink(@PathVariable String userId){
        service.sendVerificationLink(userId);
        return "Verification Link Send to Registered E-mail Id.";
    }

    @GetMapping("/reset-password/{userId}")
    public String sendResetPassword(@PathVariable String userId){
        service.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }
}
