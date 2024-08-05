//package com.musichouse.controller;

//import com.musichouse.filters.SaleFilter;
//import com.musichouse.model.service.CustomerServiceImp;
//import com.musichouse.model.service.ProductServiceImp;
//import com.musichouse.model.service.SaleServiceImp;
//import org.springframework.web.bind.annotation.*;

//import java.util.List;

//@RestController
//@RequestMapping("/{email}/sale")
//public class SaleController {
//
//    private final SaleServiceImp saleServiceImp;
//    private final CustomerServiceImp customerServiceImp;
//    private final ProductServiceImp productServiceImp;
//
//    public SaleController(SaleServiceImp saleService, CustomerServiceImp customerServiceImp, ProductServiceImp productServiceImp) {
//        this.saleServiceImp = saleService;
//        this.customerServiceImp = customerServiceImp;
//        this.productServiceImp = productServiceImp;
//    }
//
//    @GetMapping()
//    public List<Sale> getSalesByCustomer(@PathVariable String email){
//        return saleServiceImp.findByCustomer(email);
//    }
//
//    @PostMapping
//    public Sale initializeSale(@PathVariable String email){
//        Sale sale = new Sale();
//        sale.setCurrentDate();
//        sale.setCustomer(customerServiceImp.getByEmail(email).get());
//        return saleServiceImp.save(sale);
//    }
//
//    @PostMapping("/{id}/{model}")
//    public Sale addProduct(@PathVariable int id, @PathVariable String model){
//        Sale sale;
//        sale = saleServiceImp.findById(id).get();
//        sale.addProduct(productServiceImp.getByModel(model).get());
//        sale.totalPrice();
//        return saleServiceImp.save(sale);
//    }
//
//    @GetMapping
//    @RequestMapping("/")
//    public List<Sale> findWithFilters(@RequestBody SaleFilter filter){
//        return saleServiceImp.findWithFilters(filter);
//    }
//}
