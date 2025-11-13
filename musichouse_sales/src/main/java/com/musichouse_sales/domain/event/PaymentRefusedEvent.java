package com.musichouse_sales.domain.event;

import com.musichouse_sales.domain.SimpleItem;
import com.musichouse_sales.domain.Status;
import lombok.Getter;
import java.util.List;

@Getter
public class PaymentRefusedEvent extends DomainEvent {
    private final List<SimpleItem> items;

    public PaymentRefusedEvent(String saleId, List<SimpleItem> items) {
        super(saleId,Status.PAYMENT_REFUSED);
        this.items = items;
    }
}
