package com.fintechedge.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private String phoneNumber;

    @Getter
    @Setter
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

}
