package com.musichouse_sales.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data@AllArgsConstructor
public class ProductDTO {
    private String model;
    private BigDecimal price;
    private int quantity;
}


