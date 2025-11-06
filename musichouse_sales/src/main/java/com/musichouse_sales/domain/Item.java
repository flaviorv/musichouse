package com.musichouse_sales.domain;

import com.musichouse_sales.exception.InsufficientItemQuantityException;
import com.musichouse_sales.exception.ItemPriceLowerThanAllowedException;
import java.math.BigDecimal;

public record Item(String model, BigDecimal price, int stockQuantity, int quantityChosen) {
    public Item(String model, BigDecimal price, int stockQuantity, int quantityChosen) {
        if (price == null || price.signum() != 1) {
            throw new ItemPriceLowerThanAllowedException();
        }
        if (stockQuantity <= 0) {
            throw new InsufficientItemQuantityException("Stock quantity must be greater than zero.");
        }
        if (quantityChosen <= 0) {
            throw new InsufficientItemQuantityException("Quantity chosen must be greater than zero.");
        }
        if (quantityChosen > stockQuantity) {
            throw new InsufficientItemQuantityException("Quantity chosen cannot exceed stock quantity.");
        }
        this.model = model;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.quantityChosen = quantityChosen;
    }
}
