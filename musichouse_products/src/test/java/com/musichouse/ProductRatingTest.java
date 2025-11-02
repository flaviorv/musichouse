package com.musichouse;

import com.musichouse.domain.product.Amplifier;
import com.musichouse.domain.product.ElectricGuitar;
import com.musichouse.domain.product.Product;
import com.musichouse.domain.rating.ProductRating;
import com.musichouse.domain.product.ProductType;
import com.musichouse.domain.rating.Rating;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductRatingTest {
    @Test
    public void shouldInitializeAndLinkObjects() {
        Product p1 = new ElectricGuitar("VX100", ProductType.GUITAR, "Stravix", 899.99f, 5, null, 6, false);
        Product p2 = new Amplifier("AMP300", ProductType.AMPLIFIER, "RockWave", 899.00f, 4, null, 100, 12);
        ProductRating pr1 = new ProductRating("John Due", p1, Rating.THREE);

        assertEquals(Rating.THREE, pr1.getRating());
        assertEquals("John Due", pr1.getCustomer());
        assertEquals(p1, pr1.getProduct());
        assertNotEquals(p2, pr1.getProduct());
    }

}