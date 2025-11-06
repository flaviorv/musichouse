package com.musichouse_sales.dto;

import com.musichouse_sales.domain.Item;

import java.math.BigDecimal;

public record ItemResponseDTO(
        String model,
        BigDecimal price,
        int stockQuantity,
        int quantityChosen

) {
    public Item mapToItem(){
        return  new Item(model, price, stockQuantity, quantityChosen);
    }
}

