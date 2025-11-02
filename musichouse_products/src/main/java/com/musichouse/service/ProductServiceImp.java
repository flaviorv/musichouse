package com.musichouse.service;

import com.musichouse.exceptions.ResourceNotFoundException;
import com.musichouse.domain.product.Product;
import com.musichouse.dto.SaleDTO;
import com.musichouse.repository.ProductRepository;
import com.musichouse.repository.specification.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final QueryParser queryParser;

    public ProductServiceImp(ProductRepository productRepository, QueryParser queryParser) {
        this.productRepository = productRepository;
        this.queryParser = queryParser;
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
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteByModel(String model) {
        if (getByModel(model).isPresent()) {
            productRepository.deleteById(model);
        } else {
            throw new EntityNotFoundException("Product with model " + model + " not found to delete");
        }
    }

    @Override
    public void deleteAll() {
        if (getAll().isEmpty()) {
            throw new EntityNotFoundException("No products found to delete");
        }
        productRepository.deleteAll();
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void updateStock(SaleDTO sale) {
        for (SaleDTO.Product product : sale.getProducts()) {
            Optional<Product> p = getByModel(product.getModel());
            if (p.isPresent()) {
                p.get().setStock_quantity(p.get().getStock_quantity() - product.getQuantity());
                update(p.get());
            }
        }
    }

    @Override
    public List<Product> dynamicSearch(String searchText) {
        ProductSpecification spec = queryParser.matchPredicates(searchText);
        List<Product> products = productRepository.findAll(spec);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return products;
    }
}
