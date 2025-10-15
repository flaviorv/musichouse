package com.musichouse.model.service;

import com.musichouse.model.domain.Product;
import com.musichouse.model.domain.ProductType;
import com.musichouse.model.domain.Sale;
import com.musichouse.model.repository.ProductRepository;
import com.musichouse.model.repository.specification.ProductSpecification;
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
    public void updateStock(Sale sale) {
        for (Sale.Product product : sale.getProducts()) {
            Optional<Product> p = getByModel(product.getModel());
            if (p.isPresent()) {
                p.get().setQuantity(p.get().getQuantity() - product.getQuantity());
                update(p.get());
            }
        }
    }

    private ProductSpecification matchSpecs(String searchText) {
        String noPunct = searchText.toLowerCase().replaceAll("[^a-z0-9\\s]", "");
        System.out.println("noPunct------- " + noPunct);
        String[] words = noPunct.split("\\s+");
        ProductSpecification ps = new ProductSpecification();

        for (String word : words) {
            ProductType type = ProductType.search(word);
            System.out.printf("%s type", word);
            if (type != null) {
                ps.setType(type);
            }
        }

        return ps;
    }

    private List<Product> search(ProductSpecification spec) {
        return productRepository.findAll(spec);
    }

    @Override
    public List<Product> dynamicSearch(String searchText) {
        ProductSpecification spec = matchSpecs(searchText);
        List<Product> products = search(spec);
        System.out.println("Found proucts" + products);
        return products;
    }
}
