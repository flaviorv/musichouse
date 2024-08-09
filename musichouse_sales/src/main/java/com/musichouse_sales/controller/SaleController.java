package com.musichouse_sales.controller;

import com.musichouse_sales.model.domain.Product;
import com.musichouse_sales.model.domain.Sale;
import com.musichouse_sales.model.service.ProductService;
import com.musichouse_sales.model.service.SaleServiceConstants;
import com.musichouse_sales.model.service.SaleServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private static final Logger log = LoggerFactory.getLogger(SaleController.class);
    private final SaleServiceImp saleServiceImp;
    private final ProductService productService;

    public SaleController(SaleServiceImp saleServiceImp, ProductService productService){
        this.saleServiceImp = saleServiceImp;
        this.productService = productService;
    }

    @GetMapping()
    public List<Sale> getAll(){
        return saleServiceImp.getAll();
    }

    @PostMapping("/{saleId}/{model}")
    public void addProductToAnExistentSale(@PathVariable String saleId, @PathVariable String model){
        try {
            Sale sale = saleServiceImp.getById(saleId).get();
            sale.setCurrentDate();
            Product product = productService.getById(model);
            sale.addProduct(product);
            saleServiceImp.save(sale);
            log.info(SaleServiceConstants.PRODUCT_ADDED_SUCCESSFULLY, sale);
        } catch (Exception e) {
            log.error(SaleServiceConstants.CREATION_ERROR, e.getMessage());
        }
    }

    @PostMapping("/{model}")
    public void addProductToANewSale(@PathVariable String model){
        try {
            Sale sale = new Sale();
            sale.setCurrentDate();
            Product product = productService.getById(model);
            sale.addProduct(product);
            saleServiceImp.save(sale);
            log.info(SaleServiceConstants.PRODUCT_ADDED_SUCCESSFULLY, sale);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        saleServiceImp.delete(id);
    }
}
