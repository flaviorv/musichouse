package com.musichouse.adapter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("MH-SALES")
public interface SaleClient {
    @GetMapping("/deliveries/check")
    boolean isProductDelivered(@RequestParam String customerId, @RequestParam String model);
}
