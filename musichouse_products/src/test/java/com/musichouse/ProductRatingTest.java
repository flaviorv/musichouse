package com.musichouse;

import com.musichouse.model.domain.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class ProductRatingTest {
    @Test
    public void shouldInitializeAndLinkObjects() {
        Product p1 = new ElectricGuitar("VX100", ProductType.GUITAR, "Stravix", 899.99f, 5, null, 6, false);
        Product p2 = new Amplifier("AMP300", ProductType.AMPLIFIER, "RockWave", 899.00f, 4, null, 100, 12);
        ProductRating pr1 = new ProductRating("John Due", Rating.THREE);

        p1.addRating(pr1);

        assertEquals(Rating.THREE, pr1.getRating());
        assertEquals("John Due", pr1.getCustomer());
        assertEquals(p1, pr1.getProduct());
        assertNotEquals(p2, pr1.getProduct());
    }

}