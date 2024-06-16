package com.musichouse.model.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data@AllArgsConstructor@NoArgsConstructor@SuperBuilder
@Entity
public class ElectricGuitar extends Product {
    private int strings;
    private Boolean activePickup;


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
