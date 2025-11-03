package com.musichouse_sales.adapter.client;

import com.musichouse_sales.dtos.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MH-PRODUCTS")
public interface ProductClient {
    @GetMapping("/product/{model}")
    ProductDTO getById(@PathVariable String model) throws Exception;
}
