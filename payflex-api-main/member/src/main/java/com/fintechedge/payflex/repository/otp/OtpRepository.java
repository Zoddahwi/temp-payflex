package com.fintechedge.payflex.repository.otp;
import com.fintechedge.payflex.model.otp.Otp;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OtpRepository extends ReactiveCrudRepository<Otp, UUID> {
}
