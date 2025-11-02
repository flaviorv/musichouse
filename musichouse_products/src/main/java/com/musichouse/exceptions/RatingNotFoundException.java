package com.musichouse.exceptions;

import com.musichouse.model.domain.ProductRatingId;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(ProductRatingId ratingId) {
        super("Rating " + ratingId.toString() + " not found");
    }
}
