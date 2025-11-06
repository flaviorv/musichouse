package com.musichouse_sales.domain;

import com.musichouse_sales.exception.InsufficientItemQuantityException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Document("shopping_cart")
@Data@NoArgsConstructor@SuperBuilder
public class ShoppingCart {

    @Id
    @Field(name = "_id")
    private String customerId;
    private BigDecimal totalPrice = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
    private List<Item> items = new ArrayList<>();

    public ShoppingCart(String customerId) {
        this.customerId = customerId;
    }

    public void sumToTotalPrice(BigDecimal price) {
        totalPrice = totalPrice.add(price);
    }

    public void add(Item item) throws InsufficientItemQuantityException {
        sumToTotalPrice(item.price());
        items.add(item);
    }
}
