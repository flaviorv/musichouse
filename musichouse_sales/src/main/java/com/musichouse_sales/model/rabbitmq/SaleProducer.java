package com.musichouse_sales.model.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musichouse_sales.model.domain.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void close(Sale sale) throws AmqpException, JsonProcessingException {
        amqpTemplate.convertAndSend("sales_exc", "check_payment_rk", objectMapper.writeValueAsString(sale));

    }

    public void approvedPayment(Sale sale) throws AmqpException, JsonProcessingException {
        amqpTemplate.convertAndSend("sales_exc", "approved_payment_rk", objectMapper.writeValueAsString(sale));
    }

}
