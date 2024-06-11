package com.fintechedge.userservice.dto;



import com.fintechedge.userservice.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String token;
    private UserResponseDto user;

    public AuthResponse(String message) {
        this.message = message;

    }
}
