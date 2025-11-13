package com.musichouse_sales.domain.exception.item;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemPriceLowerThanAllowedException extends RuntimeException {
    public ItemPriceLowerThanAllowedException() {
        super("Item price should be greater than 0");
    }
}
