package com.musichouse_sales.controller;

import com.musichouse_sales.model.domain.Sale;
import com.musichouse_sales.model.service.SaleServiceConstants;
import com.musichouse_sales.model.service.SaleServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private static final Logger LOG = LoggerFactory.getLogger(SaleController.class);
    private final SaleServiceImp saleServiceImp;

    public SaleController(SaleServiceImp saleServiceImp){
        this.saleServiceImp = saleServiceImp;
    }

    @GetMapping()
    public List<Sale> getAll(){
        return saleServiceImp.getAll();
    }

    @PostMapping("/{saleId}/{model}")
    public ResponseEntity addProductToAnExistentSale(@PathVariable String saleId, @PathVariable String model){
        try {
            saleServiceImp.addProductToAnExistentSale(saleId, model);
            return ResponseEntity.ok(SaleServiceConstants.PRODUCT_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            LOG.error(SaleServiceConstants.CREATION_ERROR, e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{model}")
    public ResponseEntity addProductToANewSale(@PathVariable String model){
        try {
           saleServiceImp.addProductToANewSale(model);
           return ResponseEntity.ok(SaleServiceConstants.PRODUCT_ADDED_SUCCESSFULLY);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/close")
    public ResponseEntity closeSale(@RequestBody Sale _sale) throws Exception {
        try {
            Sale sale = saleServiceImp.close(_sale.getId());
            return ResponseEntity.ok(sale);
        }catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        try{
            saleServiceImp.delete(id);
            return ResponseEntity.ok().body(SaleServiceConstants.SALE_REMOVED_SUCCESSFULLY);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
