package com.fintechedge.userservice.auth.service;

import com.fintechedge.userservice.auth.service.EmailService;
import com.fintechedge.userservice.model.OtpNewUser;
import com.fintechedge.userservice.repository.OtpNewUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {
     private final EmailService emailService;
    //    private final Map<String, StoredOtpInfo> otpMap = new HashMap<>();
    private static final Duration OTP_EXPIRY_DURATION = Duration.ofMinutes(5);
    private static final int OTP_LENGTH = 8;

    private final OtpNewUserRepository otpNewUserRepository;

    @Autowired
    public OtpServiceImpl(EmailService emailService, OtpNewUserRepository otpNewUserRepository) {
        this.emailService = emailService;
        this.otpNewUserRepository = otpNewUserRepository;


    }

    public Mono<String> generateOtpAndSend(String destination) {
        String tempPassword = generateRandomPassword(OTP_LENGTH);
        Instant expiryTime = Instant.now().plus(OTP_EXPIRY_DURATION);
        if (Instant.now().isBefore(expiryTime)) {
//            kafkaTemplate.send("otp-topic", "Generated OTP", tempPassword);
            String emailContent = "Your temporary password is " + tempPassword + ". "
                    + "Please use this to login at: <a href='http://yourwebsite.com/login'>Login Page</a>";
            return emailService.sendEmail(destination, "Temporary Password", emailContent).then(Mono.just(tempPassword));
        } else {
            return Mono.error(new RuntimeException("OTP has expired"));
        }
    }

    private String generateRandomPassword(int length) {
        Random random = new Random();
        StringBuilder passwordBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = (char) ('a' + random.nextInt(26)); // Generate a random lowercase letter
            passwordBuilder.append(randomChar);
        }

        return passwordBuilder.toString();
    }

//    @Override
//    public Mono<String> generateOtpAndSend(String destination) {
//        // Generate a random string of 8 characters
//        String otp = new Random().ints(8, 'a', 'z' + 1)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
//
//        // Define default values for status and expiryTime
//        String expiryTime = LocalDateTime.now().plusMinutes(5).toString(); // OTP expires in 5 hour
//
//        // Save the OTP
////        save(destination, otp, otp, destination, expiryTime).subscribe();
//
//        String loginUrl = "http://localhost:7081/api/v1/auth/login";
//        String emailBody = "Your temporary password is: " + otp + "\nTo login, click the link below:\n" + loginUrl;
//        return emailService.sendEmail(destination, "Your Temporary Password", emailBody)
//                .thenReturn("Temporary password and login link sent successfully to " + destination);
//    }


    public Mono<OtpNewUser> save(String email, String otp, String code, String destination, String expiryTime) {
        OtpNewUser otpNewUser = new OtpNewUser();
        otpNewUser.setEmail(email);
        otpNewUser.setOtp(otp);
        otpNewUser.setCode(code);
        otpNewUser.setDestination(destination);
        otpNewUser.setExpiryTime(LocalDateTime.parse(expiryTime));
        return otpNewUserRepository.save(otpNewUser);
    }


    public Mono<String> save(OtpNewUser otp) {
        return otpNewUserRepository.save(otp).map(OtpNewUser::getOtp);
    }

    public Mono<String> sendMail(String destination, String otp) {
        return emailService.sendEmail(destination, "OTP", "Your OTP is " + otp);
    }

    public Mono<Void> save1(OtpNewUser otp) {
        return otpNewUserRepository.save(otp).then();
    }


}
