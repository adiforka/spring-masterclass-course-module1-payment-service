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

@Mapper(componentModel = "spring", uses = {FastMoneyMapper.class, ProductMapper.class})
public abstract class OrderMapper {

    @Autowired
    private ProductService productService;

    public Order toOrder(OrderDTO orderDTO) {
        List<Product> products = orderDTO.getProducts().stream()
                .map(IdDTO::getId)
                .map(productService::getById)
                .collect(toList());
        return new Order(products);
    }

    /*@Mapping(target = "id", source = "id")
    @IterableMapping(elementTargetType = IdDTO.class)
    public abstract OrderDTO toOrderDTO(Order order);*/

    public OrderDTO toOrderDTO(Order order) {
        var orderDTO = new OrderDTO();
        List<IdDTO> idDTOs = order.getProducts().stream()
                .map(Product::getId)
                .map(id -> {
                    var idDTO = new IdDTO();
                    idDTO.setId(id);
                    return idDTO;
                })
                .collect(toList());
        orderDTO.setId(order.getId());
        orderDTO.setProducts(idDTOs);
        return orderDTO;
    }

    @IterableMapping(elementTargetType = OrderDTO.class)
    public abstract List<OrderDTO> toOrderDTOList(List<Order> orders);

    public abstract PagedResultDTO<OrderDTO> toOrderPagedResultDTO(PagedResult<Order> orderPagedResult);
}