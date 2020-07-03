package com.adison.shop.orders;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapOrderRepository implements OrderRepository {

    private final Map<Long, Order> orders = new HashMap<>();
    private long index = 0;

    @Override
    public Order save(Order order) {
        order.setId(++index);
        orders.put(index, order);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public void update(Order order) {
        orders.replace(order.getId(), order);
    }
}
