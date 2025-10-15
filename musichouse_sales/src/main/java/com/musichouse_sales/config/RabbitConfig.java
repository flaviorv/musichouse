package com.musichouse_sales.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("sale_queue", true);
    }

    @Bean
    public Exchange exchange() {
        return new DirectExchange("sales_exc");
    }

    @Bean
    public Binding bindingSaleQueue() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with("payment_status_rk")
                .noargs();
    }
}
