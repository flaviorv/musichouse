package com.musichouse_sales.model.service;

import com.musichouse_sales.model.domain.Sale;
import com.musichouse_sales.model.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImp implements SaleService {
    private final SaleRepository saleRepository;

    public SaleServiceImp(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public void save(Sale sale){
        saleRepository.save(sale);
    }

    public Optional<Sale> getById(String id){
        return saleRepository.findById(id);
    }

    public List<Sale> getAll(){
        return saleRepository.findAll();
    }

    public void delete(String id){
        saleRepository.deleteById(id);
    }

    public void update(Sale sale) throws Exception {
        if(saleRepository.findById(sale.getId()).isPresent()){
            saleRepository.save(sale);
        }else{
            throw new Exception(SaleServiceConstants.ID_NOT_FOUND);
        }
    }
}
