package com.musichouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProductNotDeliveredException extends RuntimeException {
    public ProductNotDeliveredException() {
        super("It's only allowed rate products that have already been delivered");
    }
}
