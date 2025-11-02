package com.musichouse.exceptions;

public class RatingAlreadyExistsException extends RuntimeException {
    public RatingAlreadyExistsException() {
        super("It's only possible to add one rating from a predetermined customer per product");
    }
}
