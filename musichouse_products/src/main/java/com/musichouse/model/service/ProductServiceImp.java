package com.musichouse.model.service;

import com.musichouse.dto.ProductFilter;
import com.musichouse.model.domain.Product;
import com.musichouse.model.domain.Sale;
import com.musichouse.model.repository.ProductRepository;
import com.musichouse.model.repository.specification.ProductSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public void updateStock(Sale sale) {
        for(Sale.Product product : sale.getProducts()){
            Optional<Product> p = getByModel(product.getModel());
            if(p.isPresent()){
                p.get().setQuantity(p.get().getQuantity() - product.getQuantity());
                update(p.get());
            }
        }
    }

    @Override
    public List<Product> dynamicSearch(ProductFilter filter){
        Specification<Product> sp = Specification
                .where(ProductSpecification.byType(filter.type()))
                .and(ProductSpecification.byBrand(filter.brand()))
                .and(ProductSpecification.byModel(filter.model()))
                .and(ProductSpecification.byStrings(filter.strings()))
                .and(ProductSpecification.byActivePickup(filter.activePickup()))
                .and(ProductSpecification.byWatts(filter.watts()))
                .and(ProductSpecification.bySpeakerInch(filter.speakerInch()));
        return productRepository.findAll(sp);
    }
}
