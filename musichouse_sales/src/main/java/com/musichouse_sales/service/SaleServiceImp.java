package com.musichouse_sales.service;

import com.musichouse_sales.dtos.ProductDTO;
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
    private final ProductService productService;
    private final SaleProducer producer;

    public SaleServiceImp(SaleRepository saleRepository, ProductService productService, SaleProducer producer) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.producer = producer;
    }

    public Sale close(String saleId) throws Exception {
        Sale sale = getById(saleId);
        sale.setStatus(Status.CLOSED);
        producer.close(sale);
        saleRepository.save(sale);

        return sale;
    }

    public Sale addProductToANewSale(String model) throws Exception {
        Optional<Sale> openSale = saleRepository.findOpenSale(Status.OPEN);
        if (openSale.isPresent()) {
            throw new Exception(SaleServiceConstants.OPEN_SALE_ALREADY_EXISTS);
        }
        Sale sale = new Sale();
        sale.setCurrentDate();
        ProductDTO product = productService.getById(model);
        sale.addProduct(product);
        sale.setStatus(Status.OPEN);
        return saleRepository.save(sale);
    }

    public Sale addProductToAnExistentSale(String saleId, String model) throws Exception {
        Sale sale;
        try {
            sale = getById(saleId);
        } catch (Exception e) {
            throw new Exception(SaleServiceConstants.SALE_NOT_FOUND);
        }
        if(sale.getStatus() == Status.OPEN ) {
            sale.setCurrentDate();
            ProductDTO product = productService.getById(model);
            sale.addProduct(product);
            return saleRepository.save(sale);
        }else {
            throw new Exception(SaleServiceConstants.OPEN_STATUS_EXCEPTION);
        }
    }


    public Sale getById(String id) throws Exception{
        Optional<Sale> sale = saleRepository.findById(id);
        if(sale.isPresent()){
            return sale.get();
        }
        throw new Exception(SaleServiceConstants.SALE_NOT_FOUND);
    }

    public List<Sale> getAll(){
        return saleRepository.findAll();
    }

    public void delete(String id) throws Exception{
        if(!saleRepository.existsById(id)){
            throw new Exception(SaleServiceConstants.REMOVE_SALE_ERROR);
        }
        saleRepository.deleteById(id);
    }

    public void deleteAll() {
        saleRepository.deleteAll();
    }

    public Sale update(Sale sale) {
        return saleRepository.save(sale);
    }

    public Optional<Sale> findOpenSale() {
        return saleRepository.findOpenSale(Status.OPEN);
    }
}
