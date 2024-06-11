//package com.fintechedge.payflex.service.otp;
//
//import com.fintechedge.kafka.service.email.EmailService;
//import com.fintechedge.payflex.repository.otp.OtpRepository;
//import org.springframework.kafka.core.KafkaTemplate;
//
//public class OtpServiceImpBuilder {
//    private EmailService emailService;
//    private KafkaTemplate<String, String> kafkaTemplate;
//    private OtpRepository otpRepository;
//
//    public OtpServiceImpBuilder setEmailService(EmailService emailService) {
//        this.emailService = emailService;
//        return this;
//    }
//
//    public OtpServiceImpBuilder setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//        return this;
//    }
//
//    public OtpServiceImpBuilder setOtpRepository(OtpRepository otpRepository) {
//        this.otpRepository = otpRepository;
//        return this;
//    }
//
//    public OtpServiceImp createOtpServiceImp() {
//        return new OtpServiceImp(emailService, kafkaTemplate, otpRepository);
//    }
//}