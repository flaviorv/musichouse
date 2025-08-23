package com.musichouse.model.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ElectricGuitar.class, name = "guitar"),
        @JsonSubTypes.Type(value = Amplifier.class, name = "amplifier")
})
@Data@AllArgsConstructor@NoArgsConstructor@SuperBuilder
@Entity
@Slf4j
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    protected String model;
    protected String brand;
    protected float price;
    protected int quantity;
    protected byte[] image;

    public static byte[] loadImage(String resourcePath) throws IOException {
        try (InputStream is = Product.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            return is.readAllBytes();
        }
    }
}