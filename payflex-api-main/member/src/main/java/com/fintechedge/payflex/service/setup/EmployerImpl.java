package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.Employer;
import com.fintechedge.payflex.repository.setup.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class EmployerImpl implements EmployerService {

    private final EmployerRepository employerRepository;

    @Autowired
    public EmployerImpl(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    public Mono<Employer> findById(UUID id) {
        return employerRepository.findById(id);
    }
    @Override
    public Mono<Employer> save(Employer employer) {
        Employer employer1 = Employer.builder()
                .employerName(employer.getEmployerName())
                .description(employer.getDescription())
                .build();
        Mono<Employer> employers = employerRepository.save(employer1);
        return employers;
    }

    @Override
    public Flux<Employer> findAll() {
        return employerRepository.findAll();
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return employerRepository.deleteById(id);
    }

    @Override
    public Mono<Object> updateEmployer(UUID id, Employer employer) {
        return employerRepository.findById(id)
                .flatMap(existingEmployer -> {
                            existingEmployer.setEmployerName(employer.getEmployerName());
                            existingEmployer.setDescription(employer.getDescription());

                            return employerRepository.save(existingEmployer);
                        }
                );
    }


}
