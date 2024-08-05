package com.musichouse_sales.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class Product {
    private String category;
    private String model;
    private String brand;
    private float price;
}


