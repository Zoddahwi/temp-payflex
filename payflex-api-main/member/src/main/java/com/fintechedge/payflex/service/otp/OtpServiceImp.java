package com.fintechedge.payflex.service.otp;

import com.fintechedge.kafka.service.email.EmailService;
import com.fintechedge.payflex.model.otp.Otp;
import com.fintechedge.payflex.repository.otp.OtpRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.apache.poi.hssf.record.FtPioGrbitSubRecord.length;

@Service
public class OtpServiceImp implements OtpService {


    private final EmailService emailService;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OtpRepository otpRepository;

    @Autowired
    public OtpServiceImp(EmailService emailService, KafkaTemplate<String, String> kafkaTemplate, OtpRepository otpRepository) {
        this.emailService = emailService;
        this.kafkaTemplate = kafkaTemplate;
        this.otpRepository = otpRepository;
    }


    @Override
    public Mono<String> save(Otp otp) {
        return otpRepository.save(otp).map(Otp::getOtp);
    }

    @Override
    public Mono<String> sendMail(String destination, String otp) {
        return emailService.sendEmail(destination, "OTP", "Your OTP is " + otp);
    }

    @Override
    public Mono<Void> save1(Otp otp) {
        return otpRepository.save(otp).then();
    }

    @Override
    public Mono<Void> save1(Otp otp, VerificationType verificationType) {
        return otp.setVerificationType(verificationType);

    }


    private final Map<String, StoredOtpInfo> otpMap = new HashMap<>();
    private static final Duration OTP_EXPIRY_DURATION = Duration.ofMinutes(5);
    private static final int OTP_LENGTH = 8;

    public Mono<String> generateOtpAndSend(String userId) {
        String tempPassword = generateRandomPassword(OTP_LENGTH);
        Instant expiryTime = Instant.now().plus(OTP_EXPIRY_DURATION);
        if (Instant.now().isBefore(expiryTime)) {
            kafkaTemplate.send("otp-topic", "Generated OTP", tempPassword);
            String emailContent = "Your temporary password is " + tempPassword + ". "
                    + "Please use this to login at: <a href='http://yourwebsite.com/login'>Login Page</a>";
            return emailService.sendEmail(userId, "Temporary Password", emailContent).then(Mono.just(tempPassword));
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

    public Mono<VerifyOtpResult> verifyOtp(String userId, String enteredOtp) {
        kafkaTemplate.send("otp-verification-topic", "Verifying OTP" + userId, enteredOtp);
        return Mono.defer(() -> {
            StoredOtpInfo storedOtpInfo = otpMap.get(userId);

            if (storedOtpInfo != null && Instant.now().isBefore(storedOtpInfo.getExpiryTime()) && enteredOtp.equals(storedOtpInfo.getOtp())) {
                return Mono.just(new VerifyOtpResult(true, storedOtpInfo.getOtp()));
            } else {
                return Mono.just(new VerifyOtpResult(false, null));
            }
        });
    }

//    @Override
//    public Mono<String> save(Otp build) {
//        return null;
//    }

//    @Override
//    public String save(Otp build) {
//        return null;
//    }

    private String generateRandomOtp(int length) {
        Random random = new Random();
        StringBuilder otpBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otpBuilder.append(random.nextInt(10));
        }

        return otpBuilder.toString();
    }

    private static class StoredOtpInfo {
        private final String otp;
        private final Instant expiryTime;

        public StoredOtpInfo(String otp, Instant expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public Instant getExpiryTime() {
            return expiryTime;
        }
    }

    public record VerifyOtpResult(boolean isVerified, @Getter String storedOtp) {

    }
}
