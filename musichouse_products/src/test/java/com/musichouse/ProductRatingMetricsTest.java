package com.musichouse;

import com.musichouse.model.domain.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProductRatingMetricsTest {
    @Test
    public void shouldReturnTheAverageRating() {
        ProductRating pr1 = new ProductRating();
        ProductRating pr2 = new ProductRating();
        ProductRating pr3 = new ProductRating();
        Product p1 = new ElectricGuitar("VX100", ProductType.GUITAR, "Stravix", 899.99f, 5, null, 6, false);

        pr1.setRating(Rating.THREE);
        pr2.setRating(Rating.TWO);
        pr3.setRating(Rating.FIVE);
        p1.addRating(pr1);
        p1.addRating(pr2);
        p1.addRating(pr3);

        Double exp1 = (3d+2+5)/3;
        Double act1 = p1.getProductRatingMetrics().getAverageRating();

        assertEquals(exp1, act1);
    }
}
