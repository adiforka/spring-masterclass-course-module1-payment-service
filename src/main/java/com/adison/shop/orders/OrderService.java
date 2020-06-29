package com.adison.shop.orders;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public Order add(Order order) {
        return orderRepository.save(order);
    }

    public Order getById(Long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    public void update(Order order) {
        orderRepository.update(order);
    }

}
