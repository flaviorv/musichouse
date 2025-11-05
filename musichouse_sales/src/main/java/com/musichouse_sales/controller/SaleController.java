package com.musichouse_sales.controller;

import com.musichouse_sales.domain.Sale;
import com.musichouse_sales.service.SaleServiceConstants;
import com.musichouse_sales.service.SaleServiceImp;
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
    public ResponseEntity<?> byId(@RequestParam String id){
        try{
            Sale sale = saleServiceImp.getById(id);
            return ResponseEntity.ok().body(sale);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/open")
    public ResponseEntity<?> findOpenSale(){
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
    public ResponseEntity<?> addProductToAnExistentSale(@PathVariable String saleId, @PathVariable String model){
        try {
            LOG.info("Product {} added to sale {}", model, saleId);
            Sale sale = saleServiceImp.addProductToAnExistentSale(saleId, model);
            return ResponseEntity.ok(sale);
        } catch (Exception e) {
            LOG.error("{}\n{}", SaleServiceConstants.PRODUCT_ADDED_ERROR, e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @PostMapping("/{model}")
    public ResponseEntity<?> addProductToANewSale(@PathVariable String model){
        try {
           Sale sale = saleServiceImp.addProductToANewSale(model);
           LOG.info("Product {} added to a new sale ",  model);
           return ResponseEntity.ok(sale);
        } catch (Exception e) {
            LOG.error("{}\n{}", SaleServiceConstants.PRODUCT_ADDED_ERROR, e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/close")
    public ResponseEntity<?> closeSale(@RequestBody Sale _sale) {
        try {
            LOG.info("Sale {} was closed", _sale.getId());
            Sale sale = saleServiceImp.close(_sale.getId());
            return ResponseEntity.ok(sale);
        }catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        try{
            saleServiceImp.delete(id);
            LOG.info("Sale {} was deleted", id);
            return ResponseEntity.ok().body(SaleServiceConstants.SALE_REMOVED_SUCCESSFULLY);
        }catch (Exception e){
            LOG.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAll(){
        saleServiceImp.deleteAll();
        LOG.info("The removal of the sales has been completed");
        return ResponseEntity.ok().body("Sales deleted successfully");
    }
}
