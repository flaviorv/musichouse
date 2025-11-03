package com.musichouse_sales.adapter.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musichouse_sales.domain.Sale;
import com.musichouse_sales.domain.Status;
import com.musichouse_sales.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PaymentConsumer {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleProducer producer;

    private final static Logger LOG = LoggerFactory.getLogger(PaymentConsumer.class);

    @RabbitListener(queues = {"sale_queue"})
    public void receive(@Payload String json) throws Exception {

        HashMap<String, String> _sale = mapper.readValue(json, HashMap.class);

        for(String key : _sale.keySet()) {
            Sale sale = saleService.getById(key);
            String status = _sale.get(key);

            if(status.equals("Approved Payment")) {
                sale.setStatus(Status.PAID);
                producer.approvedPayment(sale);
            }else{
                sale.setStatus(Status.CANCELED);
            }
            saleService.update(sale);
        }

    }

}
