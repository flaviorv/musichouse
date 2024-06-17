package com.musichouse;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.domain.Product;
import com.musichouse.model.service.ElectricGuitarService;
import com.musichouse.model.service.ProductServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductTests {
    @Autowired
    ProductServiceImp productService;
    @Autowired
    ElectricGuitarService electricGuitarService;



    @Test
    @DisplayName("Insert test to check if quantity in register has been increased")
    void insertTest() {
        ElectricGuitar eg1 = ElectricGuitar.builder()
                .brand("Jackson")
                .model("Soloist")
                .price(7_000.99f)
                .strings(7)
                .activePickup(true)
                .build();

        ElectricGuitar eg2 = ElectricGuitar.builder()
                .brand("Jackson")
                .model("Jdr")
                .price(2_500.99f)
                .strings(6)
                .activePickup(false)
                .build();

        List<Product> all = productService.getAll();
        int initialSize = all.size();
        electricGuitarService.save(eg1);
        all = productService.getAll();
        int secondSize = all.size();
        Assertions.assertEquals(initialSize + 1, secondSize);
        electricGuitarService.save(eg2);
        all = productService.getAll();
        int thirdSize = all.size();
        Assertions.assertEquals(initialSize + 2, thirdSize);
    }

    @Test
    @DisplayName("Delete test to check if the products number has decreased")
    void deleteTest() {
        List<Product> all = productService.getAll();
        int initialSize = all.size();
        productService.deleteByModel("Jdr");
        all = productService.getAll();
        int secondSize = all.size();
        Assertions.assertEquals(initialSize - 1, secondSize);
        productService.deleteByModel("Soloist");
        all = productService.getAll();
        int thirdSize = all.size();
        Assertions.assertEquals(initialSize - 2, thirdSize);
    }

    @Test
    @DisplayName("DeleteAll test to check if all products have been deleted")
    void deleteAllTest() {
        insertTest();
        Assertions.assertEquals(2, productService.getAll().size());
        productService.deleteAll();
        Assertions.assertEquals(0, productService.getAll().size());
    }

}
