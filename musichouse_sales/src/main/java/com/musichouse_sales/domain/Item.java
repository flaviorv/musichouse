package com.musichouse_sales.domain;

import com.musichouse_sales.exception.InsufficientItemQuantityException;
import com.musichouse_sales.exception.ItemPriceLowerThanAllowedException;
import com.musichouse_sales.exception.TotalPriceParamsException;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public record Item(String model, BigDecimal price, @Transient int stockQuantity, int quantityChosen, String imagePath) {
    public Item {
        if (price == null || price.signum() != 1) {
            throw new ItemPriceLowerThanAllowedException();
        }
        if (stockQuantity <= 0) {
            throw new InsufficientItemQuantityException("Item out of stock");
        }
        if (quantityChosen <= 0) {
            throw new InsufficientItemQuantityException("Quantity chosen must be greater than zero");
        }
        if (quantityChosen > stockQuantity) {
            throw new InsufficientItemQuantityException("Quantity chosen cannot exceed stock quantity.");
        }
    }

    public static BigDecimal calculateTotalPrice(List<BigDecimal> prices, List<Integer> quantities) {

        if (prices == null || prices.isEmpty()) {
            throw new TotalPriceParamsException("Prices list is empty");
        }

        if (quantities.size() != prices.size()) {
            throw new TotalPriceParamsException("Quantities list size not equal to prices size");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (int i = 0; i < prices.size(); i++) {
            BigDecimal price = prices.get(i);
            int quantity = quantities.get(i);

            if (price == null) {
                throw new NullPointerException("Price is null");
            }

            totalPrice = totalPrice.add(BigDecimal.valueOf(quantity).multiply(price));
        }

        return totalPrice.setScale(2, RoundingMode.HALF_EVEN);

    }

}
