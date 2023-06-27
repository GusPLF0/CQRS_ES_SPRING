package com.techbank.account.commom.events;

import com.techbank.cqrs.core.events.BaseEvent;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
