package com.musichouse_sales.model.service;

import com.musichouse_sales.model.domain.Product;
import com.musichouse_sales.model.service.feign.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    private final ProductClient productClient;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public Product getById(String model) throws Exception{
        try {
            return productClient.getById(model);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(ProductServiceConstants.REQUEST_ERROR);
        }

    }
}
