package com.musichouse.model.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    @RabbitListener(queues = {"sales_queue"})
    public void receive(@Payload String json){
//        try {
            log.info(json);
//            Sale sale =  objectMapper.readValue(json, Sale.class);
//            for(Sale.Product product : sale.getProducts()){
//                log.info(product.toString());
//            }
//            return sale;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }
}
