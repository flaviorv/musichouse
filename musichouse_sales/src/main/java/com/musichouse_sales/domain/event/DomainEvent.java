package com.musichouse_sales.domain.event;

import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class DomainEvent {
    private final String aggregateId;
    private final Instant occurredOn;

    public DomainEvent(String aggregateId) {
        this.aggregateId = aggregateId;
        this.occurredOn = Instant.now();
    }

}
