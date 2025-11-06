package com.musichouse_sales.repository;

import com.musichouse_sales.domain.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String> {
}
