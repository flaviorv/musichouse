package com.musichouse_sales.domain.exception.sale;

public class NotPendingPaymentException extends RuntimeException {
    public NotPendingPaymentException(String message) {
        super(message);
    }
}
