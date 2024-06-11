package com.fintechedge.payflex.controller.setup;

import com.fintechedge.payflex.model.setup.Account;
import com.fintechedge.payflex.service.setup.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.Origin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "api/v1/account")
public class AccountController {

    private final AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    @Operation(summary = "Create an account", description = "Creates a new account")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseEntity<Account>> save(@RequestBody Account account) {
        return accountService.save(account)
                .map(savedAccount -> ResponseEntity.status(HttpStatus.CREATED).body(savedAccount));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find account by ID", description = "Returns a single account")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseEntity<Account>> findById(@PathVariable UUID id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping("")
    @Operation(summary = "Find all accounts", description = "Returns a list of accounts")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseEntity<Iterable<Account>>> findAll() {
        return accountService.findAll()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account by ID", description = "Deletes a single account")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> deleteById(@PathVariable UUID id) {
        return accountService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update account by ID", description = "Updates a single account")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseEntity<Object>> updateAccount(@PathVariable UUID id, @RequestBody Account account) {
        return accountService.updateAccount(id, account)
                .map(ResponseEntity::ok);
    }

}
