package com.musichouse.model.repository;

import com.musichouse.model.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Query("from Sale s where s.customer.email = :email")
    List<Sale> findByCustomer(String email);
}
