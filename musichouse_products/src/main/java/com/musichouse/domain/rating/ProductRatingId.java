package com.musichouse.domain.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data@NoArgsConstructor@AllArgsConstructor
public class ProductRatingId implements Serializable {
    private String customer;
    private String product;

    @Override
    public String toString() {
        return customer + "-" + product;
    }
}
