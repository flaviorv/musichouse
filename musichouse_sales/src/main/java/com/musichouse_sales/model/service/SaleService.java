package com.musichouse_sales.model.service;

import com.musichouse_sales.model.domain.Sale;
import java.util.List;

public interface SaleService {
    public void addProductToANewSale(String model) throws Exception;
    public Sale getById(String id) throws Exception;
    public List<Sale> getAll();
    public void delete(String id) throws Exception;
}
