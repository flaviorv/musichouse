package com.musichouse.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Data@AllArgsConstructor@NoArgsConstructor
@Entity@Slf4j
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    protected String model;
    @Column(name = "_type")
    protected String type;
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