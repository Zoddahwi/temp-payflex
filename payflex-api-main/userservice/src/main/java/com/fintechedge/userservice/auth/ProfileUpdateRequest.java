package com.fintechedge.userservice.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequest {
    private final String  firstname;
    private final String lastName;
    private final String phoneNumber;
    private final String email;

    public ProfileUpdateRequest(String firstname, String lastName, String phoneNumber, String email) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstname;
    }

}
