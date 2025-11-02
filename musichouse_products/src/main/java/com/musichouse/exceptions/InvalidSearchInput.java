package com.musichouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSearchInput extends RuntimeException {
    public InvalidSearchInput() {
        super("Input should not be blank, empty or contain only special characters");
    }
}
