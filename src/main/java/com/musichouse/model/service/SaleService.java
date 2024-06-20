package com.musichouse.model.service;

import com.musichouse.filters.SaleFilter;
import com.musichouse.model.domain.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    List<Sale> findByCustomer(String email);

    Sale save(Sale sale);

    Optional<Sale> findById(int id);

    List<Sale> findWithFilters(SaleFilter filters);
}
