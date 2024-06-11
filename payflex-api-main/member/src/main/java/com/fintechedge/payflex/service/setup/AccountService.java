package com.fintechedge.payflex.service.setup;

import com.fintechedge.payflex.model.setup.Account;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AccountService {
    Mono<Account> findById(UUID id);

    Mono<Account> save(Account account);

    Mono<Void> deleteById(UUID id);

    Mono<Object> updateAccount(UUID id, Account account);

    Flux<Account> findAll();

//    Mono<Account> findByAccountNumber(String accountNumber);
//
//    Mono<Account> findByAccountName(String accountName);
}
