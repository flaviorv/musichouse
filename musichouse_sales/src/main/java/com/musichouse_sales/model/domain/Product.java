package com.musichouse_sales.model.domain;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    @Setter
    @Getter
    private String model;
    private BigDecimal price;

    public Product(String model, BigDecimal price) {
        this.model = model;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }
}


