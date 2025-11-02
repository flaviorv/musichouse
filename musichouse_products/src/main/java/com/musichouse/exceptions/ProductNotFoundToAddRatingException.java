package com.musichouse.exceptions;

public class ProductNotFoundToAddRatingException extends RuntimeException {
    public ProductNotFoundToAddRatingException() {
        super("Product not found to add rating");
    }

}
