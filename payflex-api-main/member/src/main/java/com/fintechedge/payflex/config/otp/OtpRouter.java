package com.fintechedge.payflex.config.otp;

import com.fintechedge.payflex.service.otp.OtpHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class OtpRouter {
    @Bean
    public RouterFunction<ServerResponse> otpRoutes(OtpHandler otpHandler){
        return RouterFunctions.route()
                .POST("/generate-oto", RequestPredicates.accept(MediaType.APPLICATION_JSON), OtpHandler::generateOtpAndSend)
//                .POST("/generate-oto", RequestPredicates.accept(MediaType.APPLICATION_JSON), OtpHandler::generateOtp)
                .POST("verify-opt", RequestPredicates.accept(MediaType.APPLICATION_JSON), otpHandler::verifyOtp)
                .build();
    }
}
