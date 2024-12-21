package com.musichouse.controller;

import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.domain.Product;
import com.musichouse.model.service.AmplifierServiceImp;
import com.musichouse.model.service.ElectricGuitarServiceImp;
import com.musichouse.model.service.ProductServiceImp;
import com.musichouse.payload.MessagePayload;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImp productService;
    private final ElectricGuitarServiceImp electricGuitarServiceImp;
    private final AmplifierServiceImp amplifierServiceImp;

    public ProductController(ProductServiceImp productService, ElectricGuitarServiceImp electricGuitarServiceImp, AmplifierServiceImp amplifierServiceImp) {
        this.productService = productService;
        this.electricGuitarServiceImp = electricGuitarServiceImp;
        this.amplifierServiceImp = amplifierServiceImp;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Product> products = productService.getAll();
        if(products.isEmpty()) {
            ElectricGuitar e1 = ElectricGuitar.builder()
                    .model("RG121").brand("Ibanez").price(207.12f).quantity(1700).strings(6).activePickup(false).build();
            ElectricGuitar e2 = ElectricGuitar.builder()
                    .model("Soloist").brand("Jackson").price(300.54f).quantity(650).strings(7).activePickup(true).build();
            ElectricGuitar e3 = ElectricGuitar.builder()
                    .model("T500").brand("Tagima").price(400f).quantity(380).strings(6).activePickup(false).build();

            Amplifier a1 = Amplifier.builder()
                    .model("GS100").brand("Meteoro").price(1523.12f).quantity(430).speakerInch(12).watts(100).build();
            Amplifier a2 = Amplifier.builder()
                    .model("MG30FX").brand("Marshall").price(1730.12f).quantity(430).speakerInch(10).watts(30).build();

            electricGuitarServiceImp.save(e1);
            electricGuitarServiceImp.save(e2);
            electricGuitarServiceImp.save(e3);
            amplifierServiceImp.save(a1);
            amplifierServiceImp.save(a2);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("New products just arrived in stock"));
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{model}")
    public ResponseEntity<?> getByModel(@PathVariable String model) {
        Optional<Product> product = productService.getByModel(model);
        if(product.isPresent()){
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload("Model does not exist"));
    }

    @DeleteMapping("/{model}")
    public ResponseEntity<?> deleteByModel(@PathVariable String model) {
        try {
            productService.deleteByModel(model);
            return ResponseEntity.ok(new MessagePayload("Product "+model+" has been deleted"));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll(){
        try {
            productService.deleteAll();
            return ResponseEntity.ok(new MessagePayload("All products have been deleted"));
        }catch (EntityExistsException e){
            return ResponseEntity.noContent().build();
        }
    }
}
