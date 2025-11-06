package com.musichouse_sales.adapter.client;

import com.musichouse_sales.exception.ItemNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class ProductErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new ItemNotFoundException("Item not found\n" + methodKey + "\n" + response);
        }
        return defaultDecoder.decode(methodKey, response);
    }
}