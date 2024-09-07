package com.musichouse;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.service.ElectricGuitarServiceImp;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ElectricGuitarTests {
    @Autowired
    private ElectricGuitarServiceImp electricGuitarServiceImp;

    @Test
    @DisplayName("Insert test to check if the numbers of electric guitars have been deleted")
    void insertElectricGuitar() {
        ElectricGuitar eg1 = ElectricGuitar.builder()
                .brand("Jackson")
                .model("Soloist")
                .price(7_000.99f)
                .strings(7)
                .activePickup(true)
                .quantity(3)
                .build();

        ElectricGuitar eg2 = ElectricGuitar.builder()
                .brand("Jackson")
                .model("Jdr")
                .price(2_500.99f)
                .strings(6)
                .activePickup(false)
                .quantity(100)
                .build();

        List<ElectricGuitar> all = electricGuitarServiceImp.getAll();
        int initialSize = all.size();
        electricGuitarServiceImp.save(eg1);
        all = electricGuitarServiceImp.getAll();
        int secondSize = all.size();
        Assertions.assertEquals(initialSize + 1, secondSize);
        electricGuitarServiceImp.save(eg2);
        all = electricGuitarServiceImp.getAll();
        int thirdSize = all.size();
        Assertions.assertEquals(initialSize + 2, thirdSize);
    }

    @Test
    @DisplayName("Trying to save an existing electric guitar should return a exception")
    void saveFail(){
        ElectricGuitar eg1 = ElectricGuitar.builder()
                .brand("Tagima")
                .model("t350")
                .price(7_000.99f)
                .strings(7)
                .quantity(1)
                .activePickup(true)
                .build();


        electricGuitarServiceImp.save(eg1);
        Assertions.assertThrowsExactly(EntityExistsException.class, ()-> electricGuitarServiceImp.save(eg1));
    }

    @Test
    @DisplayName("GetByModel test with params that not exists should return a empty optional")
    void getFailByModel(){
        Optional<ElectricGuitar> eg1 = electricGuitarServiceImp.getByModel("t900");
        Assertions.assertTrue(eg1.isEmpty());
    }

    @Test
    @DisplayName("GetByModel test with correct params should return a optional of electric guitar ")
    void getElectricGuitarByModel(){
        Optional<ElectricGuitar> eg1 = electricGuitarServiceImp.getByModel("t350");
        Assertions.assertTrue(eg1.isPresent());
    }
}
