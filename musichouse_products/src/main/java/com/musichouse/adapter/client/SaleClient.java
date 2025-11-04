package com.musichouse.adapter.client;

import com.musichouse.dto.DeliveryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "MH-SALES", configuration = SaleClientConfiguration.class)
public interface SaleClient {
    @GetMapping("/deliveries/check")
    DeliveryResponseDTO deliveryStatus(
            @RequestParam("customerId") String customerId,
            @RequestParam("model") String model
    );
}
