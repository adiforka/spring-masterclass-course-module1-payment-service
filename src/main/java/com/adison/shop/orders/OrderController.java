package com.adison.shop.orders;

import com.adison.shop.common.web.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private UriBuilder uriBuilder;

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.toOrder(orderDTO);
        Long id = orderService.add(order).getId();
        URI locationUri = uriBuilder.requestUriWithId(id);
        return ResponseEntity.created(locationUri).build();
    }


}
