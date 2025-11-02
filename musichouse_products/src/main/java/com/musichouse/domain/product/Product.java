package com.musichouse.domain.product;

import com.musichouse.domain.rating.ProductRating;
import com.musichouse.domain.rating.ProductRatingMetrics;
import com.musichouse.domain.rating.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Slf4j
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    protected String model;
    @Column(name = "_type")
    @Enumerated(EnumType.STRING)
    protected ProductType type;
    protected String brand;
    protected float price;
    protected int stock_quantity;
    protected byte[] image;
    @Embedded
    protected ProductRatingMetrics productRatingMetrics = new ProductRatingMetrics();
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    protected List<ProductRating> productRatings = new ArrayList<>();

    public static byte[] loadImage(String resourcePath) throws IOException {
        try (InputStream is = Product.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            return is.readAllBytes();
        }
    }

    public void addRating(ProductRating pr) {
        productRatingMetrics.add(pr.getRating().getValue());
        productRatings.add(pr);
    }

    public void updateRating(ProductRating oldPr, Rating newRating) {
        Rating oldRating = oldPr.getRating();
        productRatingMetrics.update(oldRating.getValue(), newRating.getValue());
        oldPr.setRating(newRating);
    }
}