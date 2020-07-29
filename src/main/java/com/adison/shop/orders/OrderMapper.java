package com.adison.shop.orders;

import com.adison.shop.products.Product;
import com.adison.shop.products.ProductDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {


    /*Order toOrder(OrderDTO orderDTO);

    default Instant toInstant(final Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }

    @IterableMapping(elementTargetType = Product.class)
    List<Product> toProductList(List<ProductDTO> productDTOS);*/


}
