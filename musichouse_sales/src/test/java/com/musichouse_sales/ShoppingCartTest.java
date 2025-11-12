package com.musichouse_sales;

import com.musichouse_sales.domain.Item;
import com.musichouse_sales.domain.ShoppingCart;
import com.musichouse_sales.domain.SimpleItem;
import com.musichouse_sales.exception.ItemNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class ShoppingCartTest {
    @Test
    public void addTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Item i1 = new Item("AMP200", BigDecimal.valueOf(100.00), 4, 2, null);
        Item i2 = new Item("AMP300", BigDecimal.valueOf(2633.99), 8, 7, null);
        SimpleItem si1 = new SimpleItem(i1);
        SimpleItem si2 = new SimpleItem(i2);

        shoppingCart.add(si1);
        Assertions.assertEquals(1, shoppingCart.getItems().size());

        shoppingCart.add(si2);
        Assertions.assertEquals(2, shoppingCart.getItems().size());
    }

    @Test
    public void addTheSameProductShouldSumTheQuantityChosen() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Item i1 = new Item("AMP200", BigDecimal.valueOf(100.00), 4, 2, null);
        Item i2 = new Item("AMP200", BigDecimal.valueOf(100.00), 4, 3, null);
        SimpleItem si1 = new SimpleItem(i1);
        SimpleItem si2 = new SimpleItem(i2);
        shoppingCart.add(si1);
        shoppingCart.add(si2);

        Assertions.assertEquals(1,  shoppingCart.getItems().size());

        int actual = shoppingCart.getSimpleItem("AMP200").getQuantityChosen();
        Assertions.assertEquals(5,  actual);
    }

    @Test
    public void removeTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Item i1 = new Item("AMP200", BigDecimal.valueOf(100.00), 4, 2, null);
        Item i2 = new Item("AMP300", BigDecimal.valueOf(2633.99), 8, 7, null);
        SimpleItem si1 = new SimpleItem(i1);
        SimpleItem si2 = new SimpleItem(i2);
        shoppingCart.add(si1);
        shoppingCart.add(si2);

        Assertions.assertThrows(ItemNotFoundException.class, () -> shoppingCart.remove("AMP100"));

        shoppingCart.remove("AMP200");
        Assertions.assertEquals(1,  shoppingCart.getItems().size());

        shoppingCart.remove("AMP300");
        Assertions.assertEquals(0,  shoppingCart.getItems().size());
    }

    @Test
    public void tryingToRemoveAnItemThatWasNotAddedShouldReturnAnException() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Item i1 = new Item("AMP200", BigDecimal.valueOf(100.00), 4, 2, null);
        Item i2 = new Item("AMP300", BigDecimal.valueOf(2633.99), 8, 7, null);
        SimpleItem si1 = new SimpleItem(i1);
        SimpleItem si2 = new SimpleItem(i2);
        shoppingCart.add(si1);
        shoppingCart.add(si2);

        Assertions.assertThrows(ItemNotFoundException.class, () -> shoppingCart.remove("AMP100"));
    }

    @Test
    public void removeAllTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Item i1 = new Item("AMP200", BigDecimal.valueOf(100.00), 4, 2, null);
        Item i2 = new Item("AMP300", BigDecimal.valueOf(2633.99), 8, 7, null);
        SimpleItem si1 = new SimpleItem(i1);
        SimpleItem si2 = new SimpleItem(i2);
        shoppingCart.add(si1);
        shoppingCart.add(si2);

        shoppingCart.clean();

        Assertions.assertEquals(0,  shoppingCart.getItems().size());
    }

    @Test
    void updateQuantityChosenTest() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Item i1 = new Item("AMP200", BigDecimal.valueOf(100.00), 4, 2, null);
        Item i2 = new Item("AMP300", BigDecimal.valueOf(2633.99), 8, 7, null);
        SimpleItem si1 = new SimpleItem(i1);
        SimpleItem si2 = new SimpleItem(i2);
        shoppingCart.add(si1);
        shoppingCart.add(si2);

        SimpleItem _si1 = shoppingCart.getSimpleItem("AMP200");
        SimpleItem _si2 = shoppingCart.getSimpleItem("AMP300");
        Assertions.assertEquals(2, _si1.getQuantityChosen());
        Assertions.assertEquals(7, _si2.getQuantityChosen());

        Item newI1 = new Item("AMP200", BigDecimal.valueOf(100.00), 8, 7, null);
        SimpleItem newSi1 = new SimpleItem(newI1);
        shoppingCart.updateQuantityChosen(newSi1);
        Assertions.assertEquals(7, shoppingCart.getSimpleItem("AMP200").getQuantityChosen());

        Item newI2 = new Item("AMP300", BigDecimal.valueOf(2633.99), 5, 1, null);
        SimpleItem newSi2 = new SimpleItem(newI2);
        shoppingCart.updateQuantityChosen(newSi2);
        Assertions.assertEquals(1, shoppingCart.getSimpleItem("AMP300").getQuantityChosen());
    }

}
