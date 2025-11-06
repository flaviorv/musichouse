package com.musichouse_sales.service;

import com.musichouse_sales.dto.DeliveryRequestDTO;
import com.musichouse_sales.dto.DeliveryResponseDTO;

public interface DeliveryService {
    DeliveryResponseDTO checkDelivery(DeliveryRequestDTO deliveryStatus);
}
