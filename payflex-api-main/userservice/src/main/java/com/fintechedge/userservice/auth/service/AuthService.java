package com.fintechedge.userservice.auth.service;

import com.fintechedge.userservice.auth.PasswordGenerator;
import com.fintechedge.userservice.auth.ProfileUpdateRequest;
import com.fintechedge.userservice.configuration.JwtService;
import com.fintechedge.userservice.dto.AuthResponse;
import com.fintechedge.userservice.dto.LoginRequest;
import com.fintechedge.userservice.dto.RegisterRequest;
import com.fintechedge.userservice.dto.UserResponseDto;
import com.fintechedge.userservice.model.PasswordResetToken;
import com.fintechedge.userservice.model.User;
import com.fintechedge.userservice.model.VerificationToken;
//import com.fintechedge.userservice.repository.PasswordResetTokenRepository;
//import com.fintechedge.userservice.repository.VerificationTokenRepository;
import com.fintechedge.userservice.repository.PasswordResetTokenRepository;
import com.fintechedge.userservice.repository.VerificationTokenRepository;
import com.fintechedge.userservice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Calendar;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    //    public static final String ACCOUNT_SID = System.getenv("AC6963fb59b1fd0ff9ff86de0ea76c5280");
//    public static final String AUTH_TOKEN = System.getenv("cca60341dda42316275ff12424eaeb98");
//
//    private static final String SERVICE_ID = "AC6963fb59b1fd0ff9ff86de0ea76c5280";
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtService jwtService;

    private final SmsService smsService;


    public Mono<User> login(String email, String password, String username) {
        User user = userRepository.findByEmailOrUsername(email, username).block();
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return Mono.just(user);
        }
        return Mono.empty();
    }


    public Mono<AuthResponse> register(RegisterRequest request) {
        // Check if a user with the same email or username already exists
        return userRepository.findByEmailOrUsername(request.getEmail(), request.getUsername())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("Error: User with this email or username already exists"));
                    }

                    // Create a new user
                    User user = new User();
                    user.setId(request.getId());
                    user.setEmail(request.getEmail());
                    user.setPhoneNumber(request.getPhoneNumber());
                    user.setPhoneNumberVerified(false);
                    user.setUsername(request.getUsername());
                    user.setCreatedAt(Instant.now());
                    user.setUpdatedAt(Instant.now());
                    user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt the password

                    // Save the user to the database
                    return userRepository.save(user)
                            .publishOn(Schedulers.boundedElastic())
                            .publishOn(Schedulers.boundedElastic())
                            .flatMap(savedUser -> {
                                // Generate a verification token
                                String token = UUID.randomUUID().toString();
                                createVerificationTokenForUser(savedUser, token);

                                // Send a verification email
                                String url = "http://localhost:7081/user/verify?id=" + savedUser.getId() + "&token=" + token;

                                return sendEmail(savedUser.getEmail(), "Verify Email", "To verify your email, click the link below:\n" + url)
                                        .then(Mono.just(new AuthResponse("Registration successful. Check your email for a verification link.")));
                            });
                });
    }

    public Mono<AuthResponse> login(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .flatMap(user -> {
                    String token = jwtService.generateToken(user);
                    AuthResponse response = new AuthResponse();
                    UserResponseDto responseDto = new UserResponseDto();
                    responseDto.setId(user.getId());
                    responseDto.setEmail(user.getEmail());
                    responseDto.setCreatedAt(user.getCreatedAt());
                    response.setToken(token);
                    response.setMessage("Login successful");
                    return Mono.just(response);
                });
    }


// ...

    public void sendSmsVerificationCode(String phoneNumber) {

        String verificationCode = generateVerificationCode();
        smsService.sendSms("phoneNumber", phoneNumber, "Your verification code is: " + verificationCode);

    }

    private String generateVerificationCode() {
        int codeLength = 6;
        String characters = "0123456789";   // Possible characters in the code
        StringBuilder code = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            code.append(characters.charAt(randomIndex));
        }

        return code.toString();
    }


//    public void sendVerificationCode(String phoneNumber) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        Verification verification = Verification.creator(
//                        ACCOUNT_SID,
//                        phoneNumber,
//                        "sms")
//                .create();
//
//        System.out.println(verification.getStatus());
//    }

//    public Mono<String> verifyPhoneNumber(String phoneNumber, String code) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        VerificationCheck verificationCheck = VerificationCheck.creator(
//                        ACCOUNT_SID
//                )
//                .setTo(phoneNumber)
//                .create();
//
//
//        if (verificationCheck.getStatus().equals("approved")) {
//            User user = userRepository.findByPhoneNumber(phoneNumber).block();
//            if (user != null) {
//                user.setIsPhoneNumberVerified(true);
//                userRepository.save(user);
//                return Mono.just("Phone number verified successfully");
//            } else {
//                return Mono.just("No user found with the provided phone number");
//            }
//        } else {
//            return Mono.just("Invalid verification code");
//        }
//    }

//    public static void main(String[] args) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        Verification verification = Verification.creator(
//                        ACCOUNT_SID,
//                        "+233556176031",
//                        "sms")
//                .create();
//
//        System.out.println(verification.getStatus());
//    }


    public Mono<Void> sendEmail(String to, String subject, String text) {
        return Mono.fromRunnable(() -> {
            if (!EmailValidator.getInstance().isValid(to)) {
                log.error("Invalid email address: " + to);
                return;
            }
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            try {
                mailSender.send(message);
            } catch (Exception e) {
                log.error("Error sending email: " + e.getMessage());
            }
        });
    }

    public void createPasswordResetTokenForUser(UUID user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
//        passwordResetTokenRepository.save(myToken);
        String url = "http://localhost:7081/user/changePassword?id=" + user.getLeastSignificantBits() + "&token=" + token;
        sendEmail(String.valueOf(user.getLeastSignificantBits()), "Reset Password", "To reset your password, click the link below:\n" + url);
    }

    public Mono<User> createVerificationTokenForUser(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
//        verificationTokenRepository.save(myToken);
        String url = "http://localhost:7081/user/verify?id=" + user.getId() + "&token=" + token;
        sendEmail(user.getEmail(), "Verify Email", "To verify your email, click the link below:\n" + url);
        user.setToken(token);
        userRepository.save(user).subscribe();
        return Mono.just(user);
    }

    private SimpleMailMessage constructResetTokenEmail(String token, User user) {
        String url = "http://localhost:7081/user/changePassword?id=" + user.getId() + "&token=" + token;
        return constructEmail("Reset Password", "To reset your password, click the link below:\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom("mail.softfusion.io");
        return email;
    }

    public Mono<String> validatePasswordResetToken(UUID id, String token) {
        return passwordResetTokenRepository.findByToken(token)
                .flatMap(passToken -> {
                    if (passToken.getUser().getId() != id) {
                        return Mono.just("invalidToken");
                    }

                    Calendar cal = Calendar.getInstance();
                    if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                        return Mono.just("expired");
                    }

                    // token found and not expired, proceed with the password reset
                    return Mono.empty();
                })
                .defaultIfEmpty("invalidToken");
    }

    public Mono<User> changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

//    public Mono<User> createVerificationTokenForUser(User user, String token) {
//        VerificationToken myToken = new VerificationToken(token, user);
//        verificationTokenRepository.save(myToken);
//        String url = "http://localhost:7081/user/verify?id=" + user.getId() + "&token=" + token;
//        sendEmail(user.getEmail(), "Verify Email", "To verify your email, click the link below:\n" + url);
//        return Mono.just(user);
//    }

    public Mono<String> validateVerificationToken(UUID id, String token) {
        return verificationTokenRepository.findByToken(token)
                .flatMap(verifyToken -> {
                    if (verifyToken.getUser().getId().equals(id)) {
                        return Mono.just(verifyToken)
                                .filter(validToken -> {
                                    Calendar cal = Calendar.getInstance();
                                    return (validToken.getExpiryDate().getTime() - cal.getTime().getTime()) > 0;
                                })
                                .flatMap(validToken -> {
                                    User user = validToken.getUser();
                                    user.setVerified(true);
                                    userRepository.save(user).subscribe();

                                    return sendEmail(user.getEmail(), "Email Verified", "Your email has been verified successfully")
                                            .then(userRepository.save(user)
                                                    .thenReturn("verified"));
                                })
                                .defaultIfEmpty("expired");
                    } else {
                        return Mono.just("invalidToken");
                    }
                })
                .defaultIfEmpty("invalidToken");
    }

    public Mono<User> updateProfile(UUID id, ProfileUpdateRequest request) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPhoneNumber(request.getPhoneNumber());
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid user Id:" + id)));
    }


    public Mono<String> initiatePasswordReset(String email, String username) {
        if (email != null && !email.isEmpty()) {
            return userRepository.findByEmail(email)
                    .flatMap(user -> initiatePasswordResetForUser(user.getUser()));
        } else if (username != null && !username.isEmpty()) {
            return userRepository.findByUsername(username)
                    .flatMap(this::initiatePasswordResetForUser);
        } else {
            return Mono.error(new IllegalArgumentException("Email or username must be provided"));
        }
    }

    private Mono<String> initiatePasswordResetForUser(User user) {
        user.setToken(UUID.randomUUID().toString());
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user.getId(), token);
        String resetUrl = "http://localhost:7081/api/v1/auth/resetPassword?token=" + token;
        return sendEmail(user.getEmail(), "Reset Password", "To reset your password, click the link below:\n" + resetUrl)
                .thenReturn("Password reset link sent to your email");
    }


//    private Mono<User> generatePasswordResetToken(User user) {
//        user.setToken(UUID.randomUUID().toString());
//        String token = UUID.randomUUID().toString();
//        createPasswordResetTokenForUser(user.getId(), token);
//        return userRepository.save(user);
//    }

//    private Mono<String> sendPasswordResetEmail(User user) {
//        String resetUrl = "http://localhost:7081/api/v1/auth/resetPassword?token=" + user.getToken();
//        return sendEmail(user.getEmail(), "Reset Password", "To reset your password, click the link below:\n" + resetUrl)
//                .thenReturn("Password reset link sent to your email");
//    }

//    private Mono<String> initiatePasswordResetForUser(User user) {
//        return generatePasswordResetToken(user)
//                .flatMap(this::sendPasswordResetEmail);
//    }

//    public Mono<String> resetPassword(String email, String newPassword, String token) {
//        return userRepository.findByEmail(email)
//                .flatMap(user -> {
////                String token = UUID.randomUUID().toString();
//                    createPasswordResetTokenForUser(user.getId(), token);
//                    String resetUrl = "http://localhost:7081/api/v1/auth/resetPasswordPage?token=" + token;
//                    sendEmail(user.getEmail(), "Reset Password", "To reset your password, click the link below:\n" + resetUrl);
//                    return Mono.just(newPassword);
//                });
//    }

    //Todo: Link to reset password page
//    public Mono<String> resetPassword(LoginRequest request) {
//        return userRepository.findByEmail(String.valueOf(request.getEmail())
//                .flatMap(user -> {
//                    String token = UUID.randomUUID().toString();
//                    createPasswordResetTokenForUser(user.getUser(), token);
//                    String resetUrl = "http://localhost:7081/api/v1/auth/resetPasswordPage?token=" + token;
//                    sendEmail(user.getEmail(), "Reset Password", "To reset your password, click the link below:\n" + resetUrl);
//                    return Mono.just("Password reset link sent to your email");
//                });
//    }

//    public String getOtp(String phoneNumber) {
//        return "123456";
//    }
}



