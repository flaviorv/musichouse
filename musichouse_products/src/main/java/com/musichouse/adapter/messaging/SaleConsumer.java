package com.musichouse.adapter.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musichouse.dto.SaleDTO;
import com.musichouse.service.ProductServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaleConsumer {
    private final ObjectMapper objectMapper;
    private final ProductServiceImp productServiceImp;

    @RabbitListener(queues = {"product_queue"})
    public void receive(@Payload String json){
        try{
            SaleDTO sale =  objectMapper.readValue(json, SaleDTO.class);
            productServiceImp.updateStock(sale);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
