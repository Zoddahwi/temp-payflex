package com.fintechedge.payflex.service.otp;

import com.fintechedge.payflex.dto.OtpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OtpHandler {

        @Autowired
        private static OtpService otpService;

        public static Mono<ServerResponse> generateOtpAndSend(ServerRequest serverRequest){
            Mono<OtpDTO> otpDtoMono = serverRequest.bodyToMono(OtpDTO.class);
            return otpDtoMono.flatMap(otpDto -> {
                String otp = String.valueOf(otpService.generateOtpAndSend(otpDto.getEmail()));
                String expiry = "OTP expires in 5 minutes";
                String message = "Your OTP is " + otp + ". " + expiry;
                return ServerResponse.ok().bodyValue(message);
            });
        }

        public Mono<ServerResponse> verifyOtp(ServerRequest serverRequest){
            Mono<OtpDTO> otpDtoMono = serverRequest.bodyToMono(OtpDTO.class);
            return otpDtoMono.flatMap(otpDto -> {
                Mono<OtpServiceImp.VerifyOtpResult> isVerified = otpService.verifyOtp(String.valueOf(otpDto.getUserId()), otpDto.getOtp());
                return ServerResponse.ok().bodyValue(isVerified);
            });
        }


}
