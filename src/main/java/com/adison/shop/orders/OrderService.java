package com.adison.shop.orders;

import com.adison.shop.common.validator.Validate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order add(@Validate(exception = InvalidOrderException.class) Order order) {
        return orderRepository.save(order);
    }

    public Order getById(Long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    public void update(Order order) {
        //JpaRepository in SD uses save to update rows in db if an element already exists
        orderRepository.save(order);
    }

}
