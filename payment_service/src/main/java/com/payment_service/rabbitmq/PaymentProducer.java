package com.payment_service.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Object paymentStatus) throws JsonProcessingException {
        amqpTemplate.convertAndSend("sales_exc", "payment_status_rk", objectMapper.writeValueAsString(paymentStatus));
    }

}
