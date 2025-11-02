package com.musichouse.controller;

import com.musichouse.dto.ProductQuery;
import com.musichouse.domain.product.Product;
import com.musichouse.service.ProductServiceImp;
import com.musichouse.dto.MessagePayload;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImp productService;

    public ProductController(ProductServiceImp productService) {
        this.productService = productService;
    }

    @PostMapping("/search")
    public ResponseEntity<?> dynamicSearch(@RequestBody ProductQuery q) {
        String searchText = q.getQ();
        List<Product> products = productService.dynamicSearch(searchText);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("There are no products."));
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{model}")
    public ResponseEntity<?> getByModel(@PathVariable String model) {
        Optional<Product> product = productService.getByModel(model);
        if (product.isPresent()) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }

    @DeleteMapping("/{model}")
    public ResponseEntity<?> deleteByModel(@PathVariable String model) {
        try {
            productService.deleteByModel(model);
            return ResponseEntity.ok(new MessagePayload("Product " + model + " has been deleted"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        try {
            productService.deleteAll();
            return ResponseEntity.ok(new MessagePayload("All products have been deleted"));
        } catch (EntityExistsException e) {
            return ResponseEntity.noContent().build();
        }
    }

}
