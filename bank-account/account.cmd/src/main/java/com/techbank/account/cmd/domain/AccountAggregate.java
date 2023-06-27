package com.techbank.account.cmd.domain;

import com.techbank.account.cmd.api.commands.OpenAccountCommand;
import com.techbank.account.commom.events.AccountClosedEvent;
import com.techbank.account.commom.events.AccountOpenedEvent;
import com.techbank.account.commom.events.FundsDepositedEvent;
import com.techbank.account.commom.events.FundsWithdrawnEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private double balance;

    public double getBalance() {
        return this.balance;
    }

    public Boolean getActive() {
        return this.active;
    }

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountType(command.getAccountType())
                .createdDate(new Date())
                .openingBalance(command.getOpeningBalance())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be deposited into a closed account!");
        }

        if(amount <= 0) {
            throw new IllegalStateException("The deposit amount must be greater than 0");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.getId())
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Funds cannot be withdrawn into a closed account!");
        }

        if(amount <= 0) {
            throw new IllegalStateException("The withdrawn amount must be greater than 0");
        }

        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.getId())
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void closeAccount() {
        if (!this.active) {
            throw new IllegalStateException("The bank account has already been closed!");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
