package com.musichouse_sales.model.repository;

import com.musichouse_sales.model.domain.Sale;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRepository extends MongoRepository<Sale, String> {
}
