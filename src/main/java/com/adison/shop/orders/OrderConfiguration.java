package com.adison.shop.orders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderService orderService(OrderRepository orderRepository, JavaMailSender mailSender) {
        return new OrderService(orderRepository, mailSender);
    }
}
