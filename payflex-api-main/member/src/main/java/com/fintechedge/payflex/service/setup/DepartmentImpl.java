package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.Department;
import com.fintechedge.payflex.repository.setup.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class DepartmentImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    @Override
    public Mono<Department> findById(UUID id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Mono<Department> save(Department department) {
        Department department1 = Department.builder()
                .departmentName(department.getDepartmentName())
                .departmentId(UUID.randomUUID())
                .description(department.getDescription())
                .build();
        return departmentRepository.save(department1);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return departmentRepository.deleteById(id);
    }

    @Override
    public Flux<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Mono<Object> updateDepartment(UUID id, Department department) {
        return departmentRepository.findById(id)
                .flatMap(existingDepartment -> {
                            existingDepartment.setDepartmentName(department.getDepartmentName());
                            existingDepartment.setDescription(department.getDescription());

                            return departmentRepository.save(existingDepartment);
                        }
                );
    }
}
