package com.fintechedge.payflex.repository.setup;

import com.fintechedge.payflex.model.setup.DestinationBank;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DestinationBankRepository extends ReactiveCrudRepository<DestinationBank, UUID> {
}
