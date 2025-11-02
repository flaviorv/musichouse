package com.musichouse;

import com.musichouse.domain.rating.Rating;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RatingTest {
    @Test
    public void shouldReturnTheCorrespondingIntValue() {
        Rating pr1 = Rating.THREE;
        Rating pr2 = Rating.FIVE;
        Rating pr3 = Rating.ONE;

        int act1 = pr1.getValue();
        int act2 = pr2.getValue();
        int act3 = pr3.getValue();

        int exp1 = 3;
        int exp2 = 5;
        int exp3 = 1;

        assertEquals(exp1, act1);
        assertEquals(exp2, act2);
        assertEquals(exp3, act3);
    }
}
