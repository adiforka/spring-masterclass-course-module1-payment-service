package com.adison.shop.orders;

import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.common.web.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("${apiPrefix}/orders")
@RestController
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UriBuilder uriBuilder = new UriBuilder();

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        var order = orderMapper.toOrder(orderDTO);
        var orderId = orderService.add(order).getId();
        var location = uriBuilder.requestUriWithId(orderId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        var order = orderService.getById(id);
        var orderDTO = orderMapper.toOrderDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping
    public PagedResultDTO<OrderDTO> getAllOrders(@RequestParam(defaultValue = "0") int pageNumber,
                                                 @RequestParam(defaultValue = "5") int pageSize
    ) {
        var orderPagedResult = orderService.getAll(pageNumber, pageSize);
        return orderMapper.toOrderPagedResultDTO(orderPagedResult);
    }
}
