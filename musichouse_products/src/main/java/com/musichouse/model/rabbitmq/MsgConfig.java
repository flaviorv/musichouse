package com.musichouse.model.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MsgConfig {

    @Bean public Queue queue()
    {
        return new Queue("sale_queue", true);
    }

    @Bean public Queue queue2()
    {
        return new Queue("product_queue", true);
    }

    @Bean public Queue queue3()
    {
        return new Queue("payment_queue", true);
    }

    @Bean public Exchange exchange()
    {
        return new DirectExchange("sales_exc");
    }

    @Bean
    public Binding bindingSaleQueue()
    {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with("payment_status_rk")
                .noargs();
    }

    @Bean
    public Binding bindingProductQueue()
    {
        return BindingBuilder.bind(queue2())
                .to(exchange())
                .with("approved_payment_rk")
                .noargs();
    }

    @Bean
    public Binding bindingPaymentQueue()
    {
        return BindingBuilder.bind(queue3())
                .to(exchange())
                .with("check_payment_rk")
                .noargs();
    }
}