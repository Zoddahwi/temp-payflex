package com.fintechedge.payflex.model;

import com.fintechedge.payflex.model.setup.Account;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
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
    @Column("middle_name")
    private String middleName;
    @Column("last_name")
    private String lastName;
    @Column("staff_id")
    private String staffId;
    @Column("email_address")
    private String emailAddress;
    @Column("employerId")
    private String employerId;
    @Column("mobile_number")
    private String mobileNumber;
    @Column("salary")
    private String salary;
    @Column("email_verified")
    private boolean emailVerified;
    @Column("mobile_verified")
    private boolean mobileVerified;
    @Column("account_approved")
    private boolean accountApproved;
    @Column("department_id")
    private UUID departmentId;
    @Column("account_id")
    @Getter
    @Setter
    private UUID accountId;
    @Column("account")
    private Account account;

}