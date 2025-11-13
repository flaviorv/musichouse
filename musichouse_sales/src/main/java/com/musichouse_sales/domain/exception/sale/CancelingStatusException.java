package com.musichouse_sales.domain.exception.sale;

public class CancelingStatusException extends RuntimeException {
    public CancelingStatusException(String message) {
        super(message);
    }
}
