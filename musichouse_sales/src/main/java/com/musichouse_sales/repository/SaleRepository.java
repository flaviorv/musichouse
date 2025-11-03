package com.musichouse_sales.repository;

import com.musichouse_sales.domain.Sale;
import com.musichouse_sales.domain.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface SaleRepository extends MongoRepository<Sale, String> {
    @Query("{'status': ?0}")
    Optional<Sale> findOpenSale(Status status);
}
