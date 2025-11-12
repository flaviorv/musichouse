package com.musichouse_sales.domain;

import com.musichouse_sales.exception.InsufficientItemQuantityException;
import com.musichouse_sales.exception.ItemNotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.*;

@Data@NoArgsConstructor
@Document("shopping_cart")
public class ShoppingCart  {
    @Id
    @Field(name = "_id")
    private String customerId;
    private Set<SimpleItem> items = new HashSet<>();

    public ShoppingCart(String customerId) {
        this.customerId = customerId;
    }

    public void add(SimpleItem si) throws InsufficientItemQuantityException {
        if (items.contains(si)) {
            SimpleItem old = getSimpleItem(si.getModel());
            old.setQuantityChosen(old.getQuantityChosen() + si.getQuantityChosen());
            return;
        }
        items.add(si);
    }

    public void updateQuantityChosen(SimpleItem si) throws InsufficientItemQuantityException {
        if (!items.contains(si)) {
            throw new ItemNotFoundException("Item " + si.getModel() + " not found");
        }
        items.stream().filter(old -> old.equals(si)).findFirst()
                .ifPresent(old -> old.setQuantityChosen(si.getQuantityChosen()));
    }

    public void remove(String model) throws InsufficientItemQuantityException {
        items.remove(getSimpleItem(model));
    }

    public void clean() {
        this.items = new HashSet<>();
    }

    public SimpleItem getSimpleItem(String model) {
        return items.stream().filter(old -> old.getModel().equals(model)).findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item " + model + " not found"));
    }


}
