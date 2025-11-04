package com.musichouse.domain.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musichouse.domain.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Data@NoArgsConstructor
@IdClass(RatingId.class)
public class ProductRating implements Persistable<RatingId> {
    @Id
    private String customer;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product", referencedColumnName="model")
    @JsonIgnore
    private Product product;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Transient@JsonIgnore
    boolean isNew = true;

    public  ProductRating(String customer, Product product, Rating rating) {
        this.customer = customer;
        this.product = product;
        this.rating = rating;
    }

    @Override
    public RatingId getId() {
        return new RatingId(this.customer, product.getModel());
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public void markNotNew() {
        this.isNew = false;
    }

    public void markNew() {
        this.isNew = true;
    }
}
