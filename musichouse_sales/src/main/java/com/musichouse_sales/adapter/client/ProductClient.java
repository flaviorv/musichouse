package com.musichouse_sales.adapter.client;

import com.musichouse_sales.dto.ItemRequestDTO;
import com.musichouse_sales.dto.ItemResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MH-PRODUCTS")
public interface ProductClient {
    @GetMapping("/product/{model}")
    ItemResponseDTO getById(@PathVariable String model) throws Exception;
}
