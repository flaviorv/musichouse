package com.musichouse_sales.domain;

public enum Status {
    PENDING_PAYMENT,
    PAID,
    PREPARING_SHIPMENT,
    IN_SHIPPING,
    DELIVERED,
    CANCELED_BY_CLIENT,
    PAYMENT_TIMEOUT,
    PAYMENT_REFUSED,
    RETURNED
}
