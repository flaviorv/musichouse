package com.musichouse_sales.model.service;

import com.musichouse_sales.model.domain.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {
    public void save(Sale sale);
    public Optional<Sale> getById(String id);
    public List<Sale> getAll();
    public void delete(String id);
    public void update(Sale sale) throws Exception;
}
