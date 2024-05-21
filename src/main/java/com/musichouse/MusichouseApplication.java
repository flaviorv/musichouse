package com.musichouse;

import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.domain.Sale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class MusichouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusichouseApplication.class, args);
        System.out.println("Music House Application Started");

        ElectricGuitar eg1 =
                ElectricGuitar.builder()
                        .brand("Jackson")
                        .model("Soloist")
                        .price(7_000.99f)
                        .strings(7)
                        .activePickup(true)
                        .build();

        ElectricGuitar eg2 =
                ElectricGuitar.builder()
                        .brand("Jackson")
                        .model("Jdr")
                        .price(2_500.99f)
                        .strings(6)
                        .activePickup(false)
                        .build();

        ElectricGuitar eg3 =
                ElectricGuitar.builder()
                        .brand("Vintage")
                        .model("V100")
                        .price(3_500.99f)
                        .strings(6)
                        .activePickup(false)
                        .build();

        System.out.println(eg1);
        System.out.println(eg2);
        System.out.println(eg3);


        Sale s1 = new Sale();
        s1.setDate(new Date());
        s1.addProduct(eg1);
        s1.addProduct(eg2);
        s1.addProduct(eg3);

        System.out.println(s1.totalPrice());

        System.out.println(s1);




    }

}
