package com.musichouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class RatingAlreadyExistsException extends RuntimeException {
    public RatingAlreadyExistsException() {
        super("It's only possible to add one rating from a determined customer per product");
    }
}
