package com.musichouse.dto;

import lombok.Data;
import java.util.List;

@Data
public class SaleDTO {
    private String id;
    private List<SaleDTO.Product> products;

    @Data
    public static class Product{
        private String model;
        private int quantity;
    }
}
