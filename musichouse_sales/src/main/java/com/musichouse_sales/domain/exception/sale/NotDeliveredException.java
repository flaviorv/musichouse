package com.musichouse_sales.domain.exception.sale;

public class NotDeliveredException extends RuntimeException {
    public NotDeliveredException(String message) {
        super(message);
    }
}
