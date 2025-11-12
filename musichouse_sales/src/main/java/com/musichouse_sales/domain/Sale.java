package com.musichouse_sales.domain;

import com.musichouse_sales.domain.event.DomainEvent;
import com.musichouse_sales.domain.event.SaleClosedEvent;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
@Document(collection = "sales")
public class Sale {
    @Id
    private String id;
    private String customerId;
    private Date date;
    private Status status;
    private List<Item> items = new ArrayList<>();
    @Transient
    List<DomainEvent> domainEvents = new ArrayList<>();

    public Sale(ShoppingCart shoppingCart, List<Item> detailedItems) {
        this.id = UUID.randomUUID().toString();
        this.customerId = shoppingCart.getCustomerId();
        this.date = new Date();
        this.status = Status.PENDING_PAYMENT;
        this.items = detailedItems;
        List<SimpleItem> simpleItems = items.stream().map(SimpleItem::new).toList();
        this.domainEvents.add(new SaleClosedEvent(id, customerId, simpleItems));
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

}
