package com.fintechedge.payflex.controller.setup;

import com.fintechedge.payflex.model.setup.Telco;
import com.fintechedge.payflex.service.setup.TelcoService;
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
@RequestMapping(value = "/api/v1/telco")
public class TelcoController {

    private final TelcoService telcoService;

    @Autowired
    public TelcoController(TelcoService telcoService) {
        this.telcoService = telcoService;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Find telco by ID", description = "Returns a single telco")
    Mono<ResponseEntity<Telco>> findById(@PathVariable UUID id) {
        return telcoService.findById(id)
                .map(ResponseEntity::ok);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a telco", description = "Creates a new telco")
    Mono<ResponseEntity<Telco>> save(@RequestBody Telco telco) {
        return telcoService.save(telco)
                .map(savedTelco -> ResponseEntity.status(HttpStatus.CREATED).body(savedTelco));
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Find all telcos", description = "Returns a list of telcos")
    Flux<Telco> findAll() {
        return telcoService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete telco by ID", description = "Deletes a single telco")
    Mono<Void> deleteById(@PathVariable UUID id) {
        return telcoService.deleteById(id);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Update telco by ID", description = "Updates a single telco")
    Mono<ResponseEntity<Telco>> updateTelco(@PathVariable UUID id,@RequestBody Telco telco) {
        return telcoService.updateTelco(id, telco)
                .map(updatedTelco -> ResponseEntity.status(HttpStatus.CREATED).body((Telco) updatedTelco));
    }


}
