package com.fintechedge.payflex.controller.setup;


import com.fintechedge.payflex.model.setup.Department;
import com.fintechedge.payflex.repository.setup.DepartmentRepository;
import com.fintechedge.payflex.service.setup.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a department", description = "Creates a new department")
    Mono<ResponseEntity<Department>> save(@RequestBody Department department) {
        return departmentService.save(department)
                .map(savedDepartment -> ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{id}")
    @Operation(summary = "Find department by ID", description = "Returns a single department")
    Mono<ResponseEntity<Department>> findById(@PathVariable UUID id) {
        return departmentService.findById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Find all departments", description = "Returns a list of departments")
    Flux<Department> findAll() {
        return departmentService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete department by ID", description = "Deletes a single department")
    Mono<Void> deleteById(@PathVariable UUID id) {
        return departmentService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Update department by ID", description = "Updates a single department")
    Mono<ResponseEntity<Department>> updateDepartment(@PathVariable UUID id, @RequestBody Department department) {
        return departmentService.updateDepartment(id, department)
                .map(updatedDepartment -> ResponseEntity.status(HttpStatus.CREATED).body((Department) updatedDepartment));

    }
}
