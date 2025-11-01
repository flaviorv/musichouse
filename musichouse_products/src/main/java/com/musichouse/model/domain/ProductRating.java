package com.musichouse.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data@NoArgsConstructor
public class ProductRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String customer;
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @ManyToOne
    @JoinColumn(name="product_fk")
    private Product product;

    public  ProductRating(String customer, Rating rating) {
        this.customer = customer;
        this.rating = rating;
    }

}
