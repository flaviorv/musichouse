package com.musichouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MusichouseProductsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MusichouseProductsApplication.class, args);
        System.out.println("MH - PRODUCTS Started");
    }
}
