package com.musichouse_sales.domain.event;

import com.musichouse_sales.domain.SimpleItem;
import com.musichouse_sales.domain.Status;
import lombok.Getter;
import java.util.List;

@Getter
public class ItemsReturnedEvent extends DomainEvent {
    private final List<SimpleItem> items;

    public ItemsReturnedEvent(String saleId, List<SimpleItem> items) {
        super(saleId, Status.RETURNED);
        this.items = items;
    }
}
