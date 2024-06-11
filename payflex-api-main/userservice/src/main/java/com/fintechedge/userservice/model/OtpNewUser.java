package com.fintechedge.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("otp_new_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpNewUser {
    @Id
    @Column("id")
    private UUID id;
    @Column("otp")
    private String otp;
    @Column("user_id")
    private String userId;
    @Column("email")
    private String email;
    @Column("code")
    private String code;
    @Column("expiry_time")
    private LocalDateTime expiryTime;
    @Column("destination")
    private String destination;


//    public OtpNewUser(String email, String otp, String code, String destination, String expiryTime) {
//        this.email = email;
//        this.otp = otp;
//        this.code = code;
//        this.expiryTime = LocalDateTime.parse(expiryTime);
//        this.destination = destination;
//
//    }

//    @Column("verification_type")
//    private VerificationType verificationType;
//
//    public Mono<Void> setVerificationType(VerificationType verificationType) {
//        return verificationType.setVerificationType(this);
//    }
//
//    public String getDestination() {
//        switch (VerificationType.valueOf(type)) {
//            case EMAIL:
//                return email;
//            case PHONE_NUMBER:
//                return mobileNumber;
//            default:
//                return null;
//        }
//    }
}
