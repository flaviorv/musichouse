package com.musichouse.domain.product;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class ElectricGuitar extends Product {
    private int strings;
    private Boolean activePickup;

    public ElectricGuitar(String model, ProductType type, String brand, float price, int stock_quantity, byte[] image,
                          int strings,
                          boolean activePickup) {
        this.model = model;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.image = image;
        this.strings = strings;
        this.activePickup = activePickup;
    }

}
