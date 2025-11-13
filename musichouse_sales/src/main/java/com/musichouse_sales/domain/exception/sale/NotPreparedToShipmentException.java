package com.musichouse_sales.domain.exception.sale;

public class NotPreparedToShipmentException extends RuntimeException {
    public NotPreparedToShipmentException(String message) {
        super(message);
    }
}
