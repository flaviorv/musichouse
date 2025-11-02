package com.musichouse.exceptions;

public class ProductNotDeliveredException extends RuntimeException {
    public ProductNotDeliveredException() {
        super("It's only allowed rate products that have already been delivered");
    }
}
