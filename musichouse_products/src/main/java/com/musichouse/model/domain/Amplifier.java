package com.musichouse.model.domain;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data@NoArgsConstructor@Entity
public class Amplifier extends Product{
    private int watts;
    private int speakerInch;

    public Amplifier(String model, String type, String brand, float price, int quantity, byte[] image, int watts, int speakerInch) {
        this.model = model;
        this.type = type;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.watts = watts;
        this.speakerInch = speakerInch;
    }

}
