package com.musichouse_sales;

import com.musichouse_sales.model.domain.Product;
import com.musichouse_sales.model.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ProductServiceTests {

    @Test
    void checkProductRequestWorking() {
        //arrange
        ProductService productService = new ProductService();
        Product p1 = productService.getById("Jdr");
        System.out.println(p1);
    }
}
