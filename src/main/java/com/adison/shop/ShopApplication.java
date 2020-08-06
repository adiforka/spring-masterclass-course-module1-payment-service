package com.adison.shop;

import com.adison.shop.orders.Order;
import com.adison.shop.orders.OrderDTO;
import com.adison.shop.orders.OrderMapper;
import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.LocalMoney;
import com.adison.shop.products.Product;
import com.adison.shop.products.ProductService;
import com.adison.shop.products.ProductType;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.springframework.boot.SpringApplication.*;
@Log
@SpringBootApplication
public class ShopApplication {

    private static final Product PRODUCT1 = Product.builder()
            .name("Fantastic music")
            .price(LocalMoney.of(19.99))
            .type(ProductType.AUDIO)
            .description("Swell shit")
            .build();

    private static final Product PRODUCT2 = Product.builder()
            .name("Top movie")
            .price(LocalMoney.of(39.99))
            .type(ProductType.VIDEO)
            .description("Not so bad")
            .build();

    public static void main(String[] args) {
        ApplicationContext ctx = run(ShopApplication.class, args);
        var orderService = ctx.getBean(OrderService.class);
        var productService = ctx.getBean(ProductService.class);
        var orderMapper = ctx.getBean(OrderMapper.class);

        productService.add(PRODUCT1);
        productService.add(PRODUCT2);

        var order = Order.builder()
                .products(List.of(PRODUCT1, PRODUCT2))
                .timestamp(Instant.now())
                .build();

        var orderId = orderService.add(order).getId();
        var retrievedOrder = orderService.getById(orderId);
        OrderDTO orderDTO = orderMapper.toOrderDTO(retrievedOrder);
        log.info(orderDTO.toString());

    }
}
