package com.musichouse.model.repository;

import com.musichouse.model.domain.Product;
import com.musichouse.model.domain.ProductType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p.model FROM Product p")
    List<String> findAllModels();

    @Query("SELECT p.model FROM Product p WHERE p.type = :productType")
    List<String> findModelsByType(@Param("productType") ProductType pt);

    @Query("SELECT p.model FROM Product p WHERE p.brand = :brand")
    List<String> findModelsByBrand(@Param("brand") ProductType b);

    @Query("SELECT p.brand FROM Product p")
    List<String> findAllBrands();

    @Query("SELECT p.brand FROM Product p WHERE p.type = :productType")
    List<String> findBrandsByType(@Param("productType") ProductType pt);
}