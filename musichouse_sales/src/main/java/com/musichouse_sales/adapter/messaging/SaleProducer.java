package com.musichouse_sales.adapter.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musichouse_sales.domain.Sale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;
    private final static Logger LOG = LoggerFactory.getLogger(SaleProducer.class);

    public void close(Sale sale) throws AmqpException, JsonProcessingException {
        LOG.info("TO CHECK_PAYMENT_RK: CLOSED");
        amqpTemplate.convertAndSend("sales_exc", "check_payment_rk", objectMapper.writeValueAsString(sale));
    }

    public void approvedPayment(Sale sale) throws AmqpException, JsonProcessingException {
        amqpTemplate.convertAndSend("sales_exc", "approved_payment_rk", objectMapper.writeValueAsString(sale));
    }

}
