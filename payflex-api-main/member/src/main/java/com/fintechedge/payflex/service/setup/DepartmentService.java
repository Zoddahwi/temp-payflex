package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.Department;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DepartmentService {
    Mono<Department> findById(UUID id);

    Mono<Department> save(Department department);

    Mono<Void> deleteById(UUID id);

    Flux<Department> findAll();

    Mono<Object> updateDepartment(UUID id, Department department);
}
