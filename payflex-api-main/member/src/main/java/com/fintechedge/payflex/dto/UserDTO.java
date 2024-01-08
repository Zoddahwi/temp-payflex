package com.fintechedge.payflex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {

    private String firstName;
    private String lastName;
    private String staffId;
    private String emailAddress;
    private String employer;
    private String mobileNumber;
    private String accountNumber;
    private String destinationBank;
    private String salary;
    private String destinationTelco;

//    public UserDTO(UUID uuid, String userId, String wise, String s, String mail) {
//    }
}
