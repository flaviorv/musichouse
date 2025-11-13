package com.musichouse_sales.domain.event;

import com.musichouse_sales.domain.Status;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.UUID;

@Getter
@Document("domain_events")
public abstract class DomainEvent {
    @Id
    private String id =  UUID.randomUUID().toString();
    private final String aggregateId;
    private final Instant occurredOn;
    private final Status status;

    public DomainEvent(String aggregateId, Status status) {
        this.aggregateId = aggregateId;
        this.occurredOn = Instant.now();
        this.status = status;
    }

}
