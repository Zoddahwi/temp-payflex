package com.fintechedge.payflex.dto;

import com.fintechedge.payflex.service.otp.VerificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpDTO {

        private String otp;
        private String userId;
        private String email;
        private String mobileNumber;
        private String code;
        private LocalDateTime expiryTime;
        private String type;
        private String status;
        private VerificationType verificationType;
}
