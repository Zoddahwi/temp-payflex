package com.fintechedge.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewUserDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String selectcompany;
    private String password;
    private String emailId;
    private String staffId;
}
