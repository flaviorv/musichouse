package com.musichouse_sales;

import com.musichouse_sales.domain.Item;
import com.musichouse_sales.exception.InsufficientItemQuantityException;
import com.musichouse_sales.exception.ItemPriceLowerThanAllowedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class ItemTest {
    @Test
    void nonPositivePriceShouldFailInItemInstantiation() {
        Assertions.assertThrows(ItemPriceLowerThanAllowedException.class,
                () -> new Item("AMP100", new BigDecimal("0.00"), 2, 1));
        Assertions.assertThrows(ItemPriceLowerThanAllowedException.class,
                () -> new Item("AMP100", new BigDecimal("-1.00"), 5, 3));
    }

    @Test
    void nonPositiveStockQuantityShouldFailInItemInstantiation() {
        Assertions.assertThrows(InsufficientItemQuantityException.class, () ->
                new Item("AMP100", new BigDecimal("0.50"), -1, 5));
        Assertions.assertThrows(InsufficientItemQuantityException.class,
                () -> new Item("AMP100", new BigDecimal("5.00"), 0, 0));
    }

    @Test
    void nonPositiveQuantityChosenShouldFailInInstantiateItem() {
        Assertions.assertThrows(InsufficientItemQuantityException.class,
                () -> new Item("AMP100", new BigDecimal("5.00"), 6, 0));
        Assertions.assertThrows(InsufficientItemQuantityException.class,
                () -> new Item("AMP100", new BigDecimal("0.50"), 5, -1));
    }

    @Test
    void quantityChosenGreaterThanStockQuantityShouldFailInItemInstantiation() {
        Assertions.assertThrows(InsufficientItemQuantityException.class,
                () -> new Item("AMP100", new BigDecimal("0.50"), 5, 6));
    }
}
