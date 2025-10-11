package com.musichouse.model.domain;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data@NoArgsConstructor@Entity
public class ElectricGuitar extends Product {
    private int strings;
    private Boolean activePickup;

    public ElectricGuitar(String model, String type, String brand, float price, int quantity, byte[] image, int strings, boolean activePickup) {
        this.model = model;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.strings = strings;
        this.activePickup = activePickup;
    }

}
