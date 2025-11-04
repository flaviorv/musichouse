package com.musichouse.exceptions;

import com.musichouse.domain.rating.RatingId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(RatingId ratingId) {
        super("Rating " + ratingId.toString() + " not found");
    }
}
