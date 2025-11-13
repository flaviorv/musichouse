package com.musichouse_sales.domain;

import com.musichouse_sales.domain.event.*;
import com.musichouse_sales.domain.exception.sale.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "sales")
public class Sale {
    @Id
    private String id;
    private String customerId;
    private Instant date;
    private Status status;
    private List<Item> items = new ArrayList<>();
    @Transient
    List<DomainEvent> domainEvents = new ArrayList<>();

    public Sale(ShoppingCart shoppingCart, List<Item> detailedItems) {
        this.id = UUID.randomUUID().toString();
        this.customerId = shoppingCart.getCustomerId();
        this.date = Instant.now();
        this.status = Status.PENDING_PAYMENT;
        this.items = detailedItems;
        List<SimpleItem> simpleItems = items.stream().map(SimpleItem::new).toList();
        this.domainEvents.add(new SaleClosedEvent(id, simpleItems));
    }

    @Override
    public String toString(){
        return "ID: " + id + "\n" +
                "Date: " + date + "\n" +
                "Total Price: " + this.calculateTotalPrice() + "\n" +
                "Products: " + items.size() + "\n";
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Item item : items) {
            totalPrice = totalPrice.add(item.price().multiply(BigDecimal.valueOf(item.quantityChosen())));
        }

        return totalPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void markAsPaid() {
        if (status != Status.PENDING_PAYMENT) {
            throw new NotPendingPaymentException("Cannot mark as paid. Cause: sale "
                    + id + " was paid, canceled, refused, or timed out");
        }
        status = Status.PAID;
        domainEvents.add(new PaymentAcceptedEvent(id));
    }

    public void markAsInPreparation() {
        if (status != Status.PAID) {
            throw new NotPaidException("Cannot mark as preparing shipment. Cause: payment of sale "
                    + id + " was not concluded yet");
        }
        status = Status.PREPARING_SHIPMENT;
        domainEvents.add(new PreparationEvent(id));
    }

    public void markAsInShipping() {
        if (status != Status.PREPARING_SHIPMENT) {
            throw new NotPreparedToShipmentException("Cannot mark as in shipment. Cause: sale "
                    + id + " did not have its items prepared");
        }
        status = Status.IN_SHIPPING;
        domainEvents.add(new ShipmentEvent(id));
    }

    public void markAsDelivered() {
        if (status != Status.IN_SHIPPING) {
            throw new NotInShippingException("Cannot mark as delivered. Cause: sale "
                    + id + " did not have its items shipped");
        }
        status = Status.DELIVERED;
        domainEvents.add(new ShipmentEvent(id));
    }

    public void cancelSale(Instant clientRequestDate) {
        if (clientRequestDate.isAfter(date.plusSeconds(604800))) {
            throw new TimeExpiredException("Time of canceling sale was finished (7 days)");
        }

        if (!(status == Status.PENDING_PAYMENT ||
                status == Status.PAID ||
                status == Status.PREPARING_SHIPMENT ||
                status == Status.IN_SHIPPING)) {
            throw new CancelingStatusException("Cannot mark as canceled. Cause: sale "
                    + id + " is currently in an invalid state for cancellation: " + status);
        }

        status = Status.CANCELED_BY_CLIENT;
        List<SimpleItem> simpleItems = items.stream().map(SimpleItem::new).toList();
        domainEvents.add(new SaleCanceledEvent(id, simpleItems));
    }

    public void paymentTimeout() {
        if (status != Status.PENDING_PAYMENT) {
            throw new NotPendingPaymentException("Cannot cancel by payment timeout. Cause: sale "
                    + id + " was paid or canceled");
        }

        status = Status.PAYMENT_TIMEOUT;
        List<SimpleItem> simpleItems = items.stream().map(SimpleItem::new).toList();
        domainEvents.add(new PaymentTimeoutEvent(id, simpleItems));
    }

    public void paymentRefused() {
        if (status != Status.PENDING_PAYMENT) {
            throw new NotPendingPaymentException("Cannot refuse payment. Cause: sale "
                    + id + " was paid or canceled");
        }

        status = Status.PAYMENT_REFUSED;
        List<SimpleItem> simpleItems = items.stream().map(SimpleItem::new).toList();
        domainEvents.add(new PaymentRefusedEvent(id, simpleItems));
    }

    public void markAsReturned(Instant deliveredEvent, Instant clientRequest, List<SimpleItem> items) {
        if (clientRequest.isAfter(deliveredEvent.plusSeconds(604800))) {
            throw new TimeExpiredException("Time of returning sale was finished (7 days)");
        }

        if (status != Status.DELIVERED) {
            throw new NotDeliveredException("Cannot mark as returned. Cause: sale "
                    + id + " was not delivered");
        }

        status = Status.RETURNED;
        domainEvents.add(new ReturnEvent(id, items));
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> eventsToPull = new ArrayList<>(this.domainEvents);
        this.domainEvents.clear();
        return eventsToPull;
    }

}
