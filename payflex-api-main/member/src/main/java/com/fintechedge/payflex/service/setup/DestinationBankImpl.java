package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.DestinationBank;
import com.fintechedge.payflex.repository.setup.DestinationBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class DestinationBankImpl implements DestinationBankService {

    private final DestinationBankRepository destinationBankRepository;

    @Autowired
    public DestinationBankImpl(DestinationBankRepository destinationBankRepository) {
        this.destinationBankRepository = destinationBankRepository;
    }

    public Mono<DestinationBank> findById(UUID id) {
        return destinationBankRepository.findById(id);
    }

    @Override
    public Mono<DestinationBank> save(DestinationBank destinationBank) {
        DestinationBank destinationBank1 = DestinationBank.builder()
                .destinationBankName(destinationBank.getDestinationBankName())
                .description(destinationBank.getDescription())
                .build();
        return destinationBankRepository.save(destinationBank1);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return destinationBankRepository.deleteById(id);
    }

    @Override
    public Mono<Object> updateDestinationBank(UUID id, DestinationBank destinationBank) {
        return destinationBankRepository.findById(id)
                .flatMap(existingDestinationBank -> {
                            existingDestinationBank.setDestinationBankName(destinationBank.getDestinationBankName());
                            existingDestinationBank.setDescription(destinationBank.getDescription());
                            return destinationBankRepository.save(existingDestinationBank);
                        }
                );
    }

    @Override
    public Flux<DestinationBank> findAll() {
        return destinationBankRepository.findAll();
    }
}
