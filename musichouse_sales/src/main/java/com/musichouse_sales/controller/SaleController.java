package com.musichouse_sales.controller;

import com.musichouse_sales.model.domain.Sale;
import com.musichouse_sales.model.service.SaleServiceConstants;
import com.musichouse_sales.model.service.SaleServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private static final Logger LOG = LoggerFactory.getLogger(SaleController.class);
    private final SaleServiceImp saleServiceImp;

    public SaleController(SaleServiceImp saleServiceImp){
        this.saleServiceImp = saleServiceImp;
    }

    @GetMapping("/all")
    public List<Sale> getAll(){
        return saleServiceImp.getAll();
    }

    @GetMapping()
    public ResponseEntity byId(@RequestParam String id){
        try{
            Sale sale = saleServiceImp.getById(id);
            return ResponseEntity.ok().body(sale);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/open")
    public ResponseEntity findOpenSale(){
        try{
            Optional<Sale> sale = saleServiceImp.findOpenSale();
            if(sale.isPresent()){
                return ResponseEntity.ok().body(sale.get());
            }
            throw new Exception("No open sale");
        }catch (Exception e){
            LOG.error(e.getMessage());
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/{saleId}/{model}")
    public ResponseEntity addProductToAnExistentSale(@PathVariable String saleId, @PathVariable String model){
        try {
            LOG.info("Added product to sale " + saleId);
            Sale sale = saleServiceImp.addProductToAnExistentSale(saleId, model);
            return ResponseEntity.ok(sale);
        } catch (Exception e) {
            LOG.error(SaleServiceConstants.PRODUCT_ADDED_ERROR, e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @PostMapping("/{model}")
    public ResponseEntity addProductToANewSale(@PathVariable String model){
        try {
           Sale sale = saleServiceImp.addProductToANewSale(model);
           LOG.info("Added product to a new sale " + model);
           return ResponseEntity.ok(sale);
        } catch (Exception e) {
            LOG.error(SaleServiceConstants.PRODUCT_ADDED_ERROR, e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/close")
    public ResponseEntity closeSale(@RequestBody Sale _sale) {
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

    @DeleteMapping()
    public ResponseEntity deleteAll(){
        saleServiceImp.deleteAll();
        return ResponseEntity.ok().body("Sales deleted successfully");
    }
}
