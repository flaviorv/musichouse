package com.musichouse_sales.model.service.feign;

import com.musichouse_sales.model.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("http://localhost:8080")
public interface ProductClient {
    @GetMapping("/product/{model}")
    Product getById(@PathVariable String model);
}
