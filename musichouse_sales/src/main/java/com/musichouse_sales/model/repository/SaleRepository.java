package com.musichouse_sales.model.repository;

import com.musichouse_sales.model.domain.Sale;
import com.musichouse_sales.model.domain.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SaleRepository extends MongoRepository<Sale, String> {
    @Query("{'status':  ?0}")
    Optional<Sale> findOpenSale(Status status);
}
