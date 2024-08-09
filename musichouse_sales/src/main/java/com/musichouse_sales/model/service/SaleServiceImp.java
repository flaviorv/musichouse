package com.musichouse_sales.model.service;

import com.musichouse_sales.model.domain.Product;
import com.musichouse_sales.model.domain.Sale;
import com.musichouse_sales.model.repository.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImp implements SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;

    public SaleServiceImp(SaleRepository saleRepository, ProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }

    public void addProductToANewSale(String model) throws Exception {
        Sale sale = new Sale();
        sale.setCurrentDate();
        Product product = productService.getById(model);
        sale.addProduct(product);
        saleRepository.save(sale);
    }

    @PostMapping("/{saleId}/{model}")
    public void addProductToAnExistentSale(String saleId, String model) throws Exception {
        Sale sale = new Sale();
        try {
            sale = getById(saleId);
        } catch (Exception e) {
            throw new Exception(SaleServiceConstants.SALE_NOT_FOUND);
        }
        sale.setCurrentDate();
        Product product = productService.getById(model);
        sale.addProduct(product);
        saleRepository.save(sale);
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
}
