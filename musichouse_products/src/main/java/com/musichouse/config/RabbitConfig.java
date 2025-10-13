package com.musichouse.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("product_queue", true);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange("sales_exc");
    }

    @Bean
    public Binding bindingProductQueue() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with("approved_payment_rk")
                .noargs();
    }

}