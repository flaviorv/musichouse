package com.musichouse_sales.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

//anti-corruption-layer
@Data@AllArgsConstructor
public class Product {

    private String model;
    private BigDecimal price;
}


