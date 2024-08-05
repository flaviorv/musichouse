<<<<<<< HEAD
package com.musichouse;
=======
package com.musichouse_sales;
>>>>>>> 4c62a0814cd3912ae958ce015bb0434fb0dbf5dc

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
<<<<<<< HEAD
public class MusichouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusichouseApplication.class, args);
        System.out.println("Music House Application Started");

//        ElectricGuitar eg1 =
//                ElectricGuitar.builder()
//                        .brand("Jackson")
//                        .model("Soloist")
//                        .price(7_000.99f)
//                        .strings(7)
//                        .activePickup(true)
//                        .build();
//
//        ElectricGuitar eg2 =
//                ElectricGuitar.builder()
//                        .brand("Jackson")
//                        .model("Jdr")
//                        .price(2_500.99f)
//                        .strings(6)
//                        .activePickup(false)
//                        .build();
//
//        ElectricGuitar eg3 =
//                ElectricGuitar.builder()
//                        .brand("Vintage")
//                        .model("V100")
//                        .price(3_500.99f)
//                        .strings(6)
//                        .activePickup(false)
//                        .build();
//
//        System.out.println(eg1);
//        System.out.println(eg2);
//        System.out.println(eg3);
//
//
//        Sale s1 = new Sale();
//        s1.setDate(new Date());
//        s1.setCustomer(new Customer("customer1","customerTest@ct.com", "12356455345345"));
//        s1.addProduct(eg1);
//        s1.addProduct(eg2);
//        s1.addProduct(eg3);
//        s1.totalPrice();
//        System.out.println(s1);
//
//
//        Amplifier amp1 =
//                Amplifier.builder()
//                        .brand("Marshall")
//                        .model("MG15")
//                        .watts(15)
//                        .speakerInch(8)
//                        .price(1_300f)
//                        .build();
//
//        System.out.println(amp1);
//        s1.addProduct(amp1);
//        s1.totalPrice();
//        System.out.println(s1);




    }

=======
public class MusichouseSalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusichouseSalesApplication.class, args);
    }
>>>>>>> 4c62a0814cd3912ae958ce015bb0434fb0dbf5dc
}
