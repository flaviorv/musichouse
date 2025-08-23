package com.musichouse.model.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data@NoArgsConstructor@SuperBuilder
@Entity
public class Amplifier extends Product{
    private int watts;
    private int speakerInch;

    public Amplifier(String model, String brand, float price, int quantity, byte[] image, int watts, int speakerInch) {
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.watts = watts;
        this.speakerInch = speakerInch;
    }

    @Override
    public String toString() {
        return "Amplifier " +
                "[brand=" + brand +
                ", model=" + model +
                ", price=" + price +
                ", watts=" + watts +
                ", speakerInch=" + speakerInch + "]";

    }
}
