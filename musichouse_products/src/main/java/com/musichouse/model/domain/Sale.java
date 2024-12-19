package com.musichouse.model.domain;

import lombok.Data;
import java.util.List;

@Data
public class Sale {
    private String id;
    private List<Sale.Product> products;

    @Data
    public static class Product{
        private String model;
        private int quantity;
    }
}
