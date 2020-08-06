package com.adison.shop.orders;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.FastMoneyMapper;
import com.adison.shop.common.web.IdDTO;
import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.products.Product;
import com.adison.shop.products.ProductDTO;
import com.adison.shop.products.ProductMapper;
import com.adison.shop.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, FastMoneyMapper.class})
public abstract class OrderMapper {

    @Autowired
    private ProductService productService;

    public Order toOrder(OrderDTO orderDTO) {
        List<Product> products = orderDTO.getProducts().stream()
                .map(IdDTO::getId)
                .map(productService::getById)
                .collect(toList());
        //should add stuff here if I want to show more to the client (
        var order = new Order();
        order.setTimestamp(Instant.now());
        order.setProducts(products);
        return order;
    }

    @Mapping(target = "orderPlaced", source = "timestamp")
    public abstract OrderDTO toOrderDTO(Order order);

    public abstract PagedResultDTO<OrderDTO> toOrderPagedResultDTO(PagedResult<Order> orderPagedResult);
}