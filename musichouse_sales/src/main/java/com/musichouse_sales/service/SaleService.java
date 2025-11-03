package com.musichouse_sales.service;

import com.musichouse_sales.domain.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    Sale addProductToANewSale(String model) throws Exception;
    Sale addProductToAnExistentSale(String saleId, String model) throws Exception;
    Sale getById(String id) throws Exception;
    List<Sale> getAll();
    void delete(String id) throws Exception;
    void deleteAll();
    Sale update(Sale sale);
    Optional<Sale> findOpenSale();
}
