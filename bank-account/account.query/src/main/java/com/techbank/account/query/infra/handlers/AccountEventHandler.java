package com.techbank.account.query.infra.handlers;

import com.techbank.account.commom.events.AccountClosedEvent;
import com.techbank.account.commom.events.AccountOpenedEvent;
import com.techbank.account.commom.events.FundsDepositedEvent;
import com.techbank.account.commom.events.FundsWithdrawnEvent;
import com.techbank.account.query.domain.AccountRepository;
import com.techbank.account.query.domain.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountEventHandler implements EventHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        BankAccount bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        Optional<BankAccount> account = accountRepository.findById(event.getId());
        if (account.isEmpty()) {
            return;
        }

        double currentBalance = account.get().getBalance();

        double newBalance = currentBalance + event.getAmount();

        account.get().setBalance(newBalance);
        accountRepository.save(account.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        Optional<BankAccount> account = accountRepository.findById(event.getId());
        if (account.isEmpty()) {
            return;
        }

        double currentBalance = account.get().getBalance();

        double newBalance = currentBalance - event.getAmount();

        account.get().setBalance(newBalance);
        accountRepository.save(account.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        Optional<BankAccount> account = accountRepository.findById(event.getId());
        if (account.isEmpty()) {
            return;
        }

        accountRepository.deleteById(event.getId());
    }
}
