package com.musichouse_sales;

import com.musichouse_sales.model.domain.Product;
import com.musichouse_sales.model.service.ProductService;
import com.musichouse_sales.model.service.ProductServiceConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ProductServiceTests {

    @Autowired
    private ProductService productService;


    @Test
    void checkIfProductRequestWorks() {
        //arrange
        try {
            Product p1 = productService.getById("T7000");
            //act
            String expected = "T700";
            String actual = p1.getModel();
            //assert
            log.info(ProductServiceConstants.REQUEST_SUCCESS);
            Assertions.assertEquals(expected, actual);

        } catch (Exception e) {
            //act

            //assert
            Assertions.assertEquals(e.getMessage(), ProductServiceConstants.REQUEST_ERROR);
        }
    }


}
