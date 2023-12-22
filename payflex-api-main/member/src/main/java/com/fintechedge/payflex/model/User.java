package com.fintechedge.payflex.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name= "USERS", schema = "member")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;
    private String firstName;
    private String lastName;
    private String staffID;
    private String emailAddress;
    private String employer;
    private String mobileNumber;
    private String accountNumber;

    private boolean enabled;
}
