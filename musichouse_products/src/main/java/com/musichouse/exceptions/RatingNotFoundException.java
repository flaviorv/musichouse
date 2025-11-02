package com.musichouse.exceptions;

import com.musichouse.domain.rating.ProductRatingId;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(ProductRatingId ratingId) {
        super("Rating " + ratingId.toString() + " not found");
    }
}
