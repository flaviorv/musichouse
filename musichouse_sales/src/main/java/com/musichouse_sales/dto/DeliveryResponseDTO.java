package com.musichouse_sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class DeliveryResponseDTO {
    private String customerId;
    private String model;
    private boolean delivered;
}
