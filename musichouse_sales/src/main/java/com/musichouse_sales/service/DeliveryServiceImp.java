package com.musichouse_sales.service;

import com.musichouse_sales.dtos.DeliveryRequestDTO;
import com.musichouse_sales.dtos.DeliveryResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class DeliveryServiceImp implements DeliveryService {

    @Override
    public DeliveryResponseDTO checkDelivery(DeliveryRequestDTO dto) {
        //TODO
        return new DeliveryResponseDTO(dto.customerId(), dto.model(), true);
    }
}
