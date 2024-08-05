package com.musichouse_sales.model.service;

import com.musichouse_sales.model.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class ProductService {
    public Product getById(String model) throws Exception{
        RestClient restClient = RestClient.create();
        try {
            Product product = restClient.get()
                    .uri(ProductServiceConstants.SERVICE_URL(model))
                    .retrieve()
                    .toEntity(Product.class)
                    .getBody();
            return product;

        }catch (Exception e){
            throw new Exception(ProductServiceConstants.REQUEST_ERROR);
        }

    }
}
