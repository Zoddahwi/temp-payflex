package com.fintechedge.userservice.auth.service;

import reactor.core.publisher.Mono;

public interface OtpService {
    Mono<String> generateOtpAndSend(String destination);
}
