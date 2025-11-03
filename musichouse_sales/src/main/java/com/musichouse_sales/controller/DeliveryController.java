package com.musichouse_sales.controller;

import com.musichouse_sales.dtos.DeliveryRequestDTO;
import com.musichouse_sales.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/check")
    public ResponseEntity<?> getDeliveries(DeliveryRequestDTO dto) {
        return ResponseEntity.ok().body(deliveryService.checkDelivery(dto));
    }

}
