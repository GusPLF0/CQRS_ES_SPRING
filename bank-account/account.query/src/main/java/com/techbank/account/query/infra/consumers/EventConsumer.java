package com.techbank.account.query.infra.consumers;

import com.techbank.account.commom.events.AccountClosedEvent;
import com.techbank.account.commom.events.AccountOpenedEvent;
import com.techbank.account.commom.events.FundsDepositedEvent;
import com.techbank.account.commom.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);
    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
    void consume(@Payload FundsDepositedEvent event, Acknowledgment ack);
    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment ack);
}
