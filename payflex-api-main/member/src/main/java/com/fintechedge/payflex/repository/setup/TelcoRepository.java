package com.fintechedge.payflex.repository.setup;

import com.fintechedge.payflex.model.setup.Telco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelcoRepository extends ReactiveCrudRepository<Telco, UUID> {
}
