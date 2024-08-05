package com.musichouse;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.domain.Product;
import com.musichouse.model.service.ElectricGuitarServiceImp;
import com.musichouse.model.service.ProductServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Isolated
class ProductTests {
    @Autowired
    ProductServiceImp productServiceImp;
    @Autowired
    ElectricGuitarServiceImp electricGuitarService;



    @Test
    @DisplayName("Insert test to check if quantity in register has been increased")
    void insertTest() {
        ElectricGuitar eg1 = ElectricGuitar.builder()
                .brand("Ibanez")
                .model("Gio")
                .price(7_000.99f)
                .strings(7)
                .activePickup(true)
                .build();

        ElectricGuitar eg2 = ElectricGuitar.builder()
                .brand("Ibanez")
                .model("Standard")
                .price(2_500.99f)
                .strings(6)
                .activePickup(false)
                .build();

        List<Product> all = productServiceImp.getAll();
        int initialSize = all.size();
        electricGuitarService.save(eg1);
        all = productServiceImp.getAll();
        int secondSize = all.size();
        Assertions.assertEquals(initialSize + 1, secondSize);
        electricGuitarService.save(eg2);
        all = productServiceImp.getAll();
        int thirdSize = all.size();
        Assertions.assertEquals(initialSize + 2, thirdSize);
    }

    @Test
    @DisplayName("DeleteByModel test to check if the products number has decreased")
    void deleteTest() {
        List<Product> all = productServiceImp.getAll();
        int initialSize = all.size();
        productServiceImp.deleteByModel("Gio");
        all = productServiceImp.getAll();
        int secondSize = all.size();
        Assertions.assertEquals(initialSize - 1, secondSize);
        productServiceImp.deleteByModel("Standard");
        all = productServiceImp.getAll();
        int thirdSize = all.size();
        Assertions.assertEquals(initialSize - 2, thirdSize);
    }

    @Test
    @DisplayName("DeleteAll test to check if there are no products in database")
    void deleteAllTest() {
        insertTest();
        productServiceImp.deleteAll();
        Assertions.assertEquals(0, productServiceImp.getAll().size());
    }

}
