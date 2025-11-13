package com.musichouse_sales.domain.exception.sale;

public class NotPaidException extends RuntimeException {
    public NotPaidException(String message) {
        super(message);
    }
}
