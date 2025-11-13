package com.musichouse_sales.domain.exception.sale;

public class NotInShippingException extends RuntimeException {
    public NotInShippingException(String message) {
        super(message);
    }
}
