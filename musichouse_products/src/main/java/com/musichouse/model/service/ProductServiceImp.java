package com.musichouse.model.service;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.domain.Product;
import com.musichouse.model.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getByModel(String model) {
        return productRepository.findById(model);
    }

    @Override
    public void deleteByModel(String model) {
        if (getByModel(model).isPresent()) {
            productRepository.deleteById(model);
        }else {
            throw new EntityNotFoundException("Product with model " + model + " not found to delete");
        }
    }

    @Override
    public void deleteAll(){
        if(getAll().isEmpty()) {
            throw new EntityNotFoundException("No products found to delete");
        }
        productRepository.deleteAll();
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }
}
