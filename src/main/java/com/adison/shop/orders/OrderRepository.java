package com.adison.shop.orders;

import com.adison.shop.common.PagedResult;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order>  findById(Long id);

    void update(Order order);
}
