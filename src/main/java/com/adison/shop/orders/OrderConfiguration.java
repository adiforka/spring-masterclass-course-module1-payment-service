package com.adison.shop.orders;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderRepository orderRepository() {
        return new MapOrderRepository();
    }

    @Bean
    public OrderRepository hibernateOrderRepository(SessionFactory sessionFactory) {
        return new HibernateOrderRepository(sessionFactory);
    }

    @Bean
    public OrderService orderService(OrderRepository hibernateOrderRepository) {
        return new OrderService(hibernateOrderRepository);
    }
}
