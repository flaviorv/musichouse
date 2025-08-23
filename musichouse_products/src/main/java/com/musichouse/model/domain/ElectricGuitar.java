package com.musichouse.model.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data@NoArgsConstructor@SuperBuilder
@Entity
public class ElectricGuitar extends Product {
    private int strings;
    private Boolean activePickup;

    public ElectricGuitar(String model, String brand, float price, int quantity, byte[] image, int strings, boolean activePickup) {
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.strings = strings;
        this.activePickup = activePickup;
    }

    @Override
    public String toString() {
        return "ElectricGuitar " +
                "[brand=" + brand +
                ", model=" + model +
                ", price=" + price +
                ", strings=" + strings +
                ", activePickup=" + activePickup + "]";
    }
}
