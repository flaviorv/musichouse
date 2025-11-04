package com.musichouse.adapter.client;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class SaleClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new SaleErrorDecoder();
    }
}
