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
                .build();

        ElectricGuitar eg2 = ElectricGuitar.builder()
                .brand("Jackson")
                .model("Jdr")
                .price(2_500.99f)
                .strings(6)
                .activePickup(false)
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
        ElectricGuitar eg3 = ElectricGuitar.builder()
                .brand("Jackson")
                .model("Soloist")
                .price(7_000.99f)
                .strings(7)
                .activePickup(true)
                .build();

        ElectricGuitar eg4 = ElectricGuitar.builder()
                .brand("Jackson")
                .model("Soloist")
                .price(2_500.99f)
                .strings(6)
                .activePickup(false)
                .build();


        electricGuitarServiceImp.save(eg3);
        Assertions.assertThrowsExactly(EntityExistsException.class, ()-> electricGuitarServiceImp.save(eg4));
    }

    @Test
    @DisplayName("GetByModel test with params that not exists should return a empty optional")
    void getFailByModel(){
        Optional<ElectricGuitar> etest = electricGuitarServiceImp.getByModel("soloist");
        Assertions.assertTrue(etest.isEmpty());
    }

    @Test
    @DisplayName("GetByModel test with correct params should return a optional of electric guitar ")
    void getElectricGuitarByModel(){
        Optional<ElectricGuitar> etest = electricGuitarServiceImp.getByModel("Soloist");
        Assertions.assertTrue(etest.isPresent());
    }
}
