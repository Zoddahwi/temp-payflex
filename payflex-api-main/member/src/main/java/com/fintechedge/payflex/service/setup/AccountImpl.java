package com.fintechedge.payflex.service.setup;


import com.fintechedge.payflex.model.setup.Account;
import com.fintechedge.payflex.repository.setup.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class AccountImpl implements AccountService{

    private final AccountRepository accountRepository;

    @Autowired
    public AccountImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Mono<Account> findById(UUID id) {
        return accountRepository.findById(id);
    }

    @Override
    public Mono<Account> save(Account account) {
        Account account1 = Account.builder()
                .mobileMoneyNumber(account.getMobileMoneyNumber())
                .accountNumber(account.getAccountNumber())
                .bankNameId(account.getBankNameId())
                .mobileTelcoId(account.getMobileTelcoId())
                .build();
        return accountRepository.save(account1);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return accountRepository.deleteById(id);
    }

    @Override
    public Mono<Object> updateAccount(UUID id, Account account) {
        return accountRepository.findById(id)
                .flatMap(existingAccount -> {
                            existingAccount.setMobileMoneyNumber(account.getMobileMoneyNumber());
                            existingAccount.setAccountNumber(account.getAccountNumber());
                            existingAccount.setBankNameId(account.getBankNameId());
                            existingAccount.setMobileTelcoId(account.getMobileTelcoId());

                            return accountRepository.save(existingAccount);
                        }
                );
    }

    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }
}
