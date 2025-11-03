package com.musichouse_sales.service;

import com.musichouse_sales.dtos.ProductDTO;
import com.musichouse_sales.adapter.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public ProductDTO getById(String model) throws Exception{
        return productClient.getById(model);
    }
}
