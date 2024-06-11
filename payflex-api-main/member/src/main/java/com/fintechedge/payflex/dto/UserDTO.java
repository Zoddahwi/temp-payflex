package com.fintechedge.payflex.dto;

import com.fintechedge.payflex.model.User;
import com.fintechedge.payflex.model.setup.Account;
import lombok.*;

import java.util.UUID;

import static com.netflix.appinfo.AmazonInfo.MetaDataKey.accountId;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String staffId;
    private String emailAddress;
    private String employerId;
    private String mobileNumber;
    private String salary;
    private Account account;
    private UUID departmentId;
    private boolean emailVerified;
    private boolean mobileVerified;
    private boolean accountApproved;

    public UserDTO(UUID id, String firstName, String middleName, String lastName,
                   String staffId, String emailAddress, String employerId,
                   String mobileNumber, Account account, UUID departmentId,
                   String salary, boolean emailVerified, boolean mobileVerified,
                   boolean accountApproved) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.staffId = staffId;
        this.emailAddress = emailAddress;
        this.employerId = employerId;
        this.mobileNumber = mobileNumber;
        this.account = account;
        this.salary = salary;
        this.departmentId = departmentId;
        this.emailVerified = emailVerified;
        this.mobileVerified = mobileVerified;
        this.accountApproved = accountApproved;

    }

    public UserDTO(String nana,
                   String afia,
                     String kyei,
                   String number,
                   String mail,
                   String payflex,
                   String number1,
                   String number2,
                   String gtb,
                   String number3,
                   String mtn) {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.lastName = user.getLastName();
        this.staffId = user.getStaffId();
        this.emailAddress = user.getEmailAddress();
        this.employerId = user.getEmployerId();
        this.mobileNumber = user.getMobileNumber();
        this.account = user.getAccount();
        this.departmentId = user.getDepartmentId();
        this.salary = user.getSalary();
        this.emailVerified = user.isEmailVerified();
        this.mobileVerified = user.isMobileVerified();
        this.accountApproved = user.isAccountApproved();

    }

    public UserDTO(String nana, String afia, String number, String mail, String payflex, String number1, String number2, String gtb, String number3, String mtn) {
    }


    public static void setOtp(String generatedOtp) {

    }

//    public User toUser() {
//        return User.builder()
//                .id(this.id)
//                .firstName(this.firstName)
//                .middleName(this.middleName)
//                .lastName(this.lastName)
//                .staffId(this.staffId)
//                .emailAddress(this.emailAddress)
//                .employerId(this.employerId)
//                .mobileNumber(this.mobileNumber)
//                .accountId(this.account)
//                .departmentId(this.departmentId)
//                .salary(this.salary)
//                .emailVerified(this.emailVerified)
//                .mobileVerified(this.mobileVerified)
//                .accountApproved(this.accountApproved)
//                .build();
//    }
}
