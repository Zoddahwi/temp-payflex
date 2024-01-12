package com.fintechedge.payflex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Table("tbl_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column("id")
    private UUID id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("staffId")
    private String staffId;
    @Column("email_address")
    private String emailAddress;
    @Column("employer")
    private String employer;
    @Column("mobile_number")
    private String mobileNumber;
    @Column("account_number")
    private String accountNumber;
    @Column("destination_bank")
    private String destinationBank;
    @Column("salary")
    private String salary;
    @Column("destination_telco")
    private String destinationTelco;


    public User(String number, String john) {
    }
    public User(String newUser) {
    }
    public User(String nana, String afia, String number, String mail, String payflex, String number1, String number2, String gtb, String number3, String mtn) {
    }

}