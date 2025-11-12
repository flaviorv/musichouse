package com.musichouse_sales.domain;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
public class SimpleItem {
    private final String model;
    @Setter
    private int quantityChosen;

    public SimpleItem(Item item) {
        model =  item.model();
        quantityChosen = item.quantityChosen();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleItem that = (SimpleItem) o;
        return Objects.equals(model, that.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }

    public static List<String> getAllModels(Set<SimpleItem> items) {
        return items.stream().map(SimpleItem::getModel).toList();
    }
}
