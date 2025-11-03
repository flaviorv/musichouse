package com.musichouse_sales.service;

import com.musichouse_sales.dtos.DeliveryRequestDTO;
import com.musichouse_sales.dtos.DeliveryResponseDTO;

public interface DeliveryService {
    DeliveryResponseDTO checkDelivery(DeliveryRequestDTO deliveryStatus);
}
