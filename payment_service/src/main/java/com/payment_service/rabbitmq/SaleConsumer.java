package com.payment_service.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Random;

@Component
public class SaleConsumer {
    @Autowired
    private PaymentProducer producer;

    @Autowired
    private ObjectMapper mapper;

    private static final Logger LOG = LoggerFactory.getLogger(SaleConsumer.class);

    @RabbitListener(queues = {"payment_queue"})
    public void receive(@Payload String json) throws JsonProcessingException {
        JsonNode _saleId = mapper.readTree(json).get("id");
        String saleId = mapper.readValue(_saleId.toString(), String.class);

        int random = new Random().nextInt(11);
        HashMap<String, String> response = new HashMap<>();

        if (random > 4) {
            response.put(saleId, "Approved Payment");
        } else{
            response.put(saleId, "Rejected payment");
        }

        LOG.info(response.toString());
        producer.send(response);
    }
}
