package com.techbank.account.query.infra.handlers;

import com.techbank.account.commom.events.AccountClosedEvent;
import com.techbank.account.commom.events.AccountOpenedEvent;
import com.techbank.account.commom.events.FundsDepositedEvent;
import com.techbank.account.commom.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
