package com.musichouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RatingIdNotFoundOnSaleServiceException extends RuntimeException {
    public RatingIdNotFoundOnSaleServiceException(String message) {
        super(message);
    }
}
