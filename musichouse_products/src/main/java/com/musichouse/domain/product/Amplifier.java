package com.musichouse.domain.product;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Amplifier extends Product {
    private int watts;
    private int speakerInch;

    public Amplifier(String model, ProductType type, String brand, float price, int stock_quantity, byte[] image, int watts,
                     int speakerInch) {
        this.model = model;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.image = image;
        this.watts = watts;
        this.speakerInch = speakerInch;
    }

}
