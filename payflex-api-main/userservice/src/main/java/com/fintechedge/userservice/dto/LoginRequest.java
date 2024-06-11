package com.fintechedge.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
   @Getter
   @Setter
   private String username;
   private String phoneNumber;
   private String password;

    public String getEmail() {
        return username;
    }
}
