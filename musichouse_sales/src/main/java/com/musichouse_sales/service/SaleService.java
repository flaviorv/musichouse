package com.musichouse_sales.service;

import com.musichouse_sales.domain.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    Sale getById(String id) throws Exception;
    List<Sale> getAll(String customerId);
    void delete(String id) throws Exception;
    void deleteAll();
    Sale closeSale(Sale sale);
}
