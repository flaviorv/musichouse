package com.musichouse_sales.domain.event;

import com.musichouse_sales.domain.SimpleItem;
import com.musichouse_sales.domain.Status;
import lombok.Getter;
import java.util.List;

@Getter
public class SaleCanceledEvent extends DomainEvent {
    private final List<SimpleItem> items;

    public SaleCanceledEvent(String saleId, List<SimpleItem> items) {
        super(saleId, Status.CANCELED_BY_CLIENT);
        this.items = items;
    }
}
