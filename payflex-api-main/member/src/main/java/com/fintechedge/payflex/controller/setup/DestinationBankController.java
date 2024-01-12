package com.fintechedge.payflex.controller.setup;

import com.fintechedge.payflex.model.setup.DestinationBank;
import com.fintechedge.payflex.service.setup.DestinationBankService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/destination-bank")
public class DestinationBankController {

    private final DestinationBankService destinationBankService;
    @Autowired
    public DestinationBankController(DestinationBankService destinationBankService) {
        this.destinationBankService = destinationBankService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find destination bank by ID", description = "Returns a single destination bank")
    Mono<ResponseEntity<DestinationBank>> findById(@PathVariable UUID id) {
        return destinationBankService.findById(id)
                .map(ResponseEntity::ok);
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a destination bank", description = "Creates a new destination bank")
    Mono<ResponseEntity<DestinationBank>> save(@RequestBody DestinationBank destinationBank) {
        return destinationBankService.save(destinationBank)
                .map(savedDestinationBank -> ResponseEntity.status(HttpStatus.CREATED).body(savedDestinationBank));
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Find all destination banks", description = "Returns a list of destination banks")
    Flux<DestinationBank> findAll() {
        return destinationBankService.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete destination bank by ID", description = "Deletes a single destination bank")
    Mono<ResponseEntity<DestinationBank>> updateDestinationBank(@PathVariable UUID id,@RequestBody DestinationBank destinationBank) {
        return destinationBankService.updateDestinationBank(id, destinationBank)
                   .map(updatedDestinationBank -> ResponseEntity.status(HttpStatus.CREATED).body((DestinationBank) updatedDestinationBank));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete destination bank by ID", description = "Deletes a single destination bank")
    Mono<Void> deleteById(@PathVariable UUID id) {
        return destinationBankService.deleteById(id);
    }


}
