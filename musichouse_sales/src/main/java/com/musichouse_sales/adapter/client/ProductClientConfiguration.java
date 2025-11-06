package com.musichouse_sales.adapter.client;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class ProductClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ProductErrorDecoder();
    }
}
