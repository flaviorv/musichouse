package com.musichouse.model.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Amplifier extends Product{
    private int watts;
    private int speakerInch;

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
