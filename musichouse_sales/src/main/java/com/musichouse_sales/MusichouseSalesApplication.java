package com.musichouse_sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MusichouseSalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusichouseSalesApplication.class, args);
    }
}
