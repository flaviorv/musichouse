package com.musichouse_sales;

import com.musichouse_sales.model.domain.Product;
import com.musichouse_sales.model.domain.Sale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Date;

@SpringBootTest
class MusichouseSalesApplicationTests {

    @Test
    void checkIfTheDateIsNotNull(){
        //arrange
        Sale s1 = new Sale();
        s1.setCurrentDate();
        //act
        Date s1Date = s1.getDate();
        //assert
        Assertions.assertNotNull(s1Date);
    }

    @Test
    void checkIfTheNullDateThrowsException() {
        //arrange
        Sale s1 = new Sale();
        //assert
        Assertions.assertThrows(NullPointerException.class, () -> {
            //act
            Date nullDate = s1.getDate();
        });
    }

    @Test
    void checkTheSumOfThePrice() {
        //arrange
        Product p1 = new Product("NJ3234", 1000.43f );
        Product p2 = new Product("HFAL99LKA", 2034.32f);
        Sale s1 = new Sale();
        s1.addProduct(p1);
        s1.addProduct(p2);
        //act
        Float actualPrice = s1.totalPrice();
        Float expectedPrice = (p1.getPrice() + p2.getPrice());
        //assert
        Assertions.assertEquals(actualPrice, expectedPrice);

        //arrange
        Sale s2 = new Sale();
        s2.addProduct(p1);
        //act
        actualPrice = s2.totalPrice();
        expectedPrice = (p1.getPrice());
        //assert
        Assertions.assertEquals(actualPrice, expectedPrice);

        //arrange
        Sale s3 = new Sale();
        //act
        actualPrice = s3.totalPrice();
        expectedPrice = (0f);
        //assert
        Assertions.assertEquals(actualPrice, expectedPrice);

    }

}
