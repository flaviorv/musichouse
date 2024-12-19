package com.musichouse.model.service;

import com.musichouse.model.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    Optional<Product> getByModel(String model);

    void deleteByModel(String model);

    void deleteAll();

    Product update(Product product);
}
