package com.adison.shop.orders;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.validator.Validate;
import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order add(@Validate(exception = InvalidOrderException.class) Order order) {
        order.setTimestamp(Instant.now());
        order.setPayment(Payment.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .money(order.getTotalPrice())
                .build());
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

    public PagedResult<Order> getAll(int pageNumber, int pageSize) {
        var orderPage = orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PagedResult<>(orderPage.getContent(), pageNumber, orderPage.getTotalPages());
    }

}
