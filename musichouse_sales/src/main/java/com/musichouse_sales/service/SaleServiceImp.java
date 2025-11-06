package com.musichouse_sales.service;

import com.musichouse_sales.domain.Sale;
import com.musichouse_sales.domain.Status;
import com.musichouse_sales.adapter.messaging.SaleProducer;
import com.musichouse_sales.repository.SaleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImp implements SaleService {
    private final SaleRepository saleRepository;
    private final SaleProducer producer;

    public SaleServiceImp(SaleRepository saleRepository, SaleProducer producer) {
        this.saleRepository = saleRepository;
        this.producer = producer;
    }

    public Sale close(String saleId) throws Exception {
        Sale sale = getById(saleId);
        sale.setStatus(Status.CLOSED);
        producer.close(sale);
        saleRepository.save(sale);

        return sale;
    }


    public Sale getById(String id) throws Exception{
        Optional<Sale> sale = saleRepository.findById(id);
        if(sale.isPresent()){
            return sale.get();
        }
        throw new Exception(SaleServiceConstants.SALE_NOT_FOUND);
    }

    @Override
    public List<Sale> getAll(String customerId){
        return saleRepository.findAll();
    }

    @Override
    public void delete(String id) throws Exception{
        if(!saleRepository.existsById(id)){
            throw new Exception(SaleServiceConstants.REMOVE_SALE_ERROR);
        }
        saleRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        saleRepository.deleteAll();
    }

    @Override
    public Sale closeSale(Sale sale) {
        return null;
    }
}
