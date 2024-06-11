package com.fintechedge.payflex.model.otp;

import com.fintechedge.payflex.service.otp.VerificationType;
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

@Table("tbl_otp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Otp {
    @Id
    @Column("id")
    private UUID id;
    @Column("otp")
    private String otp;
    @Column("user_id")
    private String userId;
    @Column("email")
    private String email;
    @Column("mobile_number")
    private String mobileNumber;
    @Column("code")
    private String code;
    @Column("expiry_time")
    private LocalDateTime expiryTime;
    @Column("type")
    private String type;
    @Column("status")
    private String status;

    @Column("verification_type")
    private VerificationType verificationType;

    public Mono<Void> setVerificationType(VerificationType verificationType) {
        return verificationType.setVerificationType(this);
    }

    public String getDestination() {
        switch (VerificationType.valueOf(type)) {
            case EMAIL:
                return email;
            case PHONE_NUMBER:
                return mobileNumber;
            default:
                return null;
        }
    }
}
