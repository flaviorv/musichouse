package com.musichouse_sales.model.service;

import com.musichouse_sales.model.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductService {
    public Product getById(String model){
        RestClient restClient = RestClient.create();
        String serverUrl = String.format("http://localhost:8080/product/%s", model);
        Product product = restClient.get()
                .uri(serverUrl)
                .retrieve()
                .toEntity(Product.class)
                .getBody();
        return product;
    }
}
