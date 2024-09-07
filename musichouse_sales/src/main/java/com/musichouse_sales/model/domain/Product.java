package com.musichouse_sales.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data@AllArgsConstructor
public class Product {

    private String model;
    private BigDecimal price;
    private int quantity;

}


