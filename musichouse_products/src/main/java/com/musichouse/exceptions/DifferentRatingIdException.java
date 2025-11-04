package com.musichouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DifferentRatingIdException extends RuntimeException {
    public DifferentRatingIdException(String message) {
        super(message);
    }
}
