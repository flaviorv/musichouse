package com.musichouse.service;

import com.musichouse.domain.product.Product;
import com.musichouse.dto.SaleDTO;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();

    Optional<Product> getByModel(String model);

    void save(Product product);

    void deleteByModel(String model);

    void deleteAll();

    Product update(Product product);

    void updateStock(SaleDTO sale);

    List<Product> dynamicSearch(String searchText);

}
