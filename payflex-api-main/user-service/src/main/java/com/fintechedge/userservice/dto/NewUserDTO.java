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
    private String firstName;
    private String lastName;
    private String selectCompany;
    private String email;
    private String staffId;
}
