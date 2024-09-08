package com.musichouse_sales;

import com.musichouse_sales.model.domain.Product;
import com.musichouse_sales.model.domain.Sale;
import com.musichouse_sales.model.repository.SaleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@SpringBootTest
class SaleTests {

    @Autowired
    private SaleRepository saleRepository;

    @Test
    @DisplayName("Check if the date is not null after attribution")
    void dateIsNotNull(){
        //arrange
        Sale s1 = new Sale();
        s1.setCurrentDate();
        //act
        Date s1Date = s1.getDate();
        //assert
        Assertions.assertNotNull(s1Date);
    }

    @Test
    @DisplayName("Check if the null date throws a exception")
    void dateIsNullException() {
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
        Product p1 = new Product("NJ3234", BigDecimal.valueOf(1000.43), 30 );
        Product p2 = new Product("HFAL99LKA", BigDecimal.valueOf(2034.32),223 );
        Sale s1 = new Sale();
        s1.addProduct(p1);
        s1.addProduct(p2);
        //act
        BigDecimal expectedPrice = p1.getPrice().add(p2.getPrice());
        BigDecimal actualPrice = s1.getTotalPrice();
        //assert
        Assertions.assertEquals(actualPrice, expectedPrice);

        //arrange
        Sale s2 = new Sale();
        s2.addProduct(p1);
        //act
        expectedPrice = p1.getPrice();
        actualPrice = s2.getTotalPrice();
        //assert
        Assertions.assertEquals(actualPrice, expectedPrice);

    }

    @Test
    @DisplayName("Check if an unassigned totalPrice returns 0.00 (this is also a test for two decimal places)")
    void checkPrices(){
        //arrange
        Sale s1 = new Sale();
        //act
        BigDecimal expected = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal actual = s1.getTotalPrice();
        //assert
        Assertions.assertEquals(expected, actual);

        //arrange
        Product p1 = new Product("NJ3234", BigDecimal.valueOf(1000), 1930);
        s1.addProduct(p1);
        //act
        expected = new BigDecimal(1000).setScale(2, RoundingMode.HALF_EVEN);
        actual = s1.getTotalPrice();
        //assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Check if the quantity 0 of the product in stock throws a exception")
    void checkTheQuantityInStock(){
        //arrange
        Sale s1 = new Sale();
        Product p1 = new Product("K232", BigDecimal.valueOf(1023), 0);
        Product p2 = new Product("LS4", BigDecimal.valueOf(1238), -1);

        //assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            //act
            s1.addProduct(p1);
        });
        //assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            //act
            s1.addProduct(p2);
        });
    }

    @Test
    @DisplayName("Check if a quantity greater than 0 of a product in the stock allows to add the product.")
    void checkQuantityGreaterThan0(){
        //arrange
        Sale s1 = new Sale();
        Product p1 = new Product("K232", BigDecimal.valueOf(1023), 1);
        Product p2 = new Product("LS4", BigDecimal.valueOf(1238), 2);

        //act
        s1.addProduct(p1);
        s1.addProduct(p2);
        int actual = s1.getProducts().size();
        int expected = 2;

        //assert
        Assertions.assertEquals(expected, actual);

    }

}
