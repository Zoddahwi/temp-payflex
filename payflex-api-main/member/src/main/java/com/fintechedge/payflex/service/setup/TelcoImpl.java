package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.Telco;
import com.fintechedge.payflex.repository.setup.TelcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class TelcoImpl implements TelcoService{
    private final TelcoRepository telcoRepository;

    @Autowired
    public TelcoImpl(TelcoRepository telcoRepository) {
        this.telcoRepository = telcoRepository;
    }

    @Override

    public Mono<Telco> findById(UUID id) {
        return telcoRepository.findById(id);
    }


    @Override
    public Mono<Telco> save(Telco telco) {
        Telco telco1 = Telco.builder()
                .telcoName(telco.getTelcoName())
                .description(telco.getDescription())
                .build();
        Mono<Telco> telcos = telcoRepository.save(telco1);
        return telcos;
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return telcoRepository.deleteById(id);
    }

    @Override
    public Mono<Object> updateTelco(UUID id, Telco telco) {
        return telcoRepository.findById(id)
                .flatMap(existingTelco -> {
                            existingTelco.setTelcoName(telco.getTelcoName());
                            existingTelco.setDescription(telco.getDescription());

                            return telcoRepository.save(existingTelco);
                        }
                );
    }

    @Override
    public Flux<Telco> findAll() {
        return telcoRepository.findAll();
    }

}
