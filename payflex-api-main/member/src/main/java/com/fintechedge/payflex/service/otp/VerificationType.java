package com.fintechedge.payflex.service.otp;

import com.fintechedge.payflex.model.otp.Otp;
import reactor.core.publisher.Mono;

public enum VerificationType {

    EMAIL {
        @Override
        public Mono<Void> setDestination(Otp otp) {
            otp.setEmail(otp.getDestination());
            return Mono.empty();
        }
    },
    PHONE_NUMBER {
        @Override
        public Mono<Void> setDestination(Otp otp) {
            otp.setMobileNumber(otp.getDestination());
            return Mono.empty();
        }
    };

    public abstract Mono<Void> setDestination(Otp otp);

    public Mono<Void> setVerificationType(Otp otp) {
        return setDestination(otp);
    }


//    EMAIL,
//    PHONE_NUMBER;
//
//    public Mono<Void> setVerificationType(Otp otp) {
//        switch (this) {
//            case EMAIL:
//                otp.setEmail(otp.getDestination());
//                break;
//            case PHONE_NUMBER:
//                otp.setMobileNumber(otp.getDestination());
//                break;
//        }
//        return Mono.empty();
//    }
}
