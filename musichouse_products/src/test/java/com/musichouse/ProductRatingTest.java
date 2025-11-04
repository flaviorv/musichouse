package com.musichouse;

import com.musichouse.domain.product.Amplifier;
import com.musichouse.domain.product.ElectricGuitar;
import com.musichouse.domain.product.Product;
import com.musichouse.domain.rating.ProductRating;
import com.musichouse.domain.product.ProductType;
import com.musichouse.domain.rating.Rating;
import com.musichouse.dto.RatingRequestDTO;
import com.musichouse.dto.RatingResponseDTO;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductRatingTest {
    @Test
    public void shouldInitializeAndLinkObjects() {
        Product p1 = new ElectricGuitar("VX100", ProductType.GUITAR, "Stravix", 899.99f, 5, null, 6, false);
        Product p2 = new Amplifier("AMP300", ProductType.AMPLIFIER, "RockWave", 899.00f, 4, null, 100, 12);
        ProductRating pr1 = new ProductRating("John Due", p1, Rating.THREE);
        p1.addRating(pr1);

        assertEquals(Rating.THREE, p1.getProductRatings().get(0).getRating());
        assertEquals("John Due", p1.getProductRatings().get(0).getCustomer());
        assertEquals(p1, p1.getProductRatings().get(0).getProduct());
        assertNotEquals(p2, p1.getProductRatings().get(0).getProduct());
    }

}