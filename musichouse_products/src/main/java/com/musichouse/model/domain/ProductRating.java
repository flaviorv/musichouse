package com.musichouse.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@NoArgsConstructor
@IdClass(ProductRatingId.class)
public class ProductRating {
    @Id
    private String customer;

    @Id
    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName="model")
    @JsonIgnore
    private Product product;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    public  ProductRating(String customer, Product product, Rating rating) {
        this.customer = customer;
        this.product = product;
        this.rating = rating;
    }

}
