package com.musichouse.model.service;

import com.musichouse.model.repository.specification.ProductSpecification;
import com.musichouse.model.domain.Product;
import com.musichouse.model.domain.Sale;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    Optional<Product> getByModel(String model);

    void save(Product product);

    void deleteByModel(String model);

    void deleteAll();

    Product update(Product product);

    void updateStock(Sale sale);

    List<Product> search(ProductSpecification spec);

}
