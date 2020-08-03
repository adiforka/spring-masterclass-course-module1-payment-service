package com.adison.shop.orders;

import com.adison.shop.common.web.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("${apiPrefix}/orders")
@RestController
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UriBuilder uriBuilder = new UriBuilder();

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        var order = orderMapper.toOrder(orderDTO);
        var id = orderService.add(order).getId();
        var location = uriBuilder.requestUriWithId(id);
        return ResponseEntity.created(location).build();
    }
}
