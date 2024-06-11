package com.fintechedge.payflex.controller.setup;

import com.fintechedge.payflex.model.setup.Employer;
import com.fintechedge.payflex.service.setup.EmployerService;
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
@RequestMapping(value = "/api/v1/employer")
public class EmployerController {

    private final EmployerService employerService;


    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find employer by ID", description = "Returns a single employer")
    Mono<ResponseEntity<Employer>> findById(@PathVariable UUID id) {
        return employerService.findById(id)
                .map(ResponseEntity::ok);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an employer", description = "Creates a new employer")
    Mono<ResponseEntity<Employer>> save(@RequestBody Employer employer) {
        return employerService.save(employer)
                .map(savedEmployer -> ResponseEntity.status(HttpStatus.CREATED).body(savedEmployer));
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Find all employers", description = "Returns a list of employers")
    Flux<Employer> findAll() {
        return employerService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete employer by ID", description = "Deletes a single employer")
    Mono<Void> deleteById(@PathVariable UUID id) {
        return employerService.deleteById(id);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Update employer by ID", description = "Updates a single employer")
    Mono<ResponseEntity<Employer>> updateEmployer(@PathVariable UUID id,@RequestBody Employer employer) {
        return employerService.updateEmployer(id, employer)
                .map(updatedEmployer -> ResponseEntity.status(HttpStatus.CREATED).body((Employer) updatedEmployer));
    }



}
