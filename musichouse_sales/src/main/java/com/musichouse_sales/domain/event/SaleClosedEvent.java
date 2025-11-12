package com.musichouse_sales.domain.event;

import com.musichouse_sales.domain.SimpleItem;
import com.musichouse_sales.domain.Status;
import lombok.Getter;

import java.util.List;

@Getter
public class SaleClosedEvent extends DomainEvent {
    private final String customerId;
    private final Status status;
    private final List<SimpleItem> items;

    public SaleClosedEvent(String saleId, String customerId, List<SimpleItem> items) {
        super(saleId);
        this.customerId = customerId;
        this.status = Status.PENDING_PAYMENT;
        this.items = items;
    }

}
