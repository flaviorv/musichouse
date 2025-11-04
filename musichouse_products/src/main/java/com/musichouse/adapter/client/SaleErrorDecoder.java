package com.musichouse.adapter.client;

import com.musichouse.exceptions.RatingIdNotFoundOnSaleServiceException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class SaleErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new RatingIdNotFoundOnSaleServiceException(methodKey);
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
