package com.fintechedge.payflex.service.otp;

import com.fintechedge.payflex.model.otp.Otp;
import reactor.core.publisher.Mono;

public interface OtpService {


//    String generateOtpAndSend(String destination, String userId);

    Mono<String> generateOtpAndSend(String destination);
    Mono<OtpServiceImp.VerifyOtpResult> verifyOtp(String destination, String otp);

    Mono<String> save(Otp build);

    Mono<String> sendMail(String destination, String otp);

    Mono<Void> save1(Otp otp);

    Mono<Void> save1(Otp otp, VerificationType verificationType);

//    Mono<User> verifyOtp();

//    String generateOtp(String email, String otpDtoEmail);
//    boolean verifyOtp(String userId, String otp);

}
