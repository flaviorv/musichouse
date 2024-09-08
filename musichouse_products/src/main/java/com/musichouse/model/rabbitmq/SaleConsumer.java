package com.musichouse.model.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musichouse.model.domain.Product;
import com.musichouse.model.domain.Sale;
import com.musichouse.model.service.ProductServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaleConsumer {
    private final ObjectMapper objectMapper;
    private final ProductServiceImp productServiceImp;
    @RabbitListener(queues = {"product_queue"})
    public void receive(@Payload String json){
        try {
            Sale sale =  objectMapper.readValue(json, Sale.class);
            for(Sale.Product product : sale.getProducts()){
                Optional<Product> p = productServiceImp.getByModel(product.getModel());
                if(p.isPresent()){
                    p.get().setQuantity(p.get().getQuantity() - product.getQuantity());
                    log.info(p.get().getModel() + " quantity was updated from : " + (p.get().getQuantity() + product.getQuantity()) + " to " + p.get().getQuantity());
                    productServiceImp.update(p.get());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
