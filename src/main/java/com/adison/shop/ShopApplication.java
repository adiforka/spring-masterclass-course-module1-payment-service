package com.adison.shop;

import com.adison.shop.orders.Order;
import com.adison.shop.orders.OrderMapper;
import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.LocalMoney;
import com.adison.shop.products.Product;
import com.adison.shop.products.ProductService;
import com.adison.shop.products.ProductType;
import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.util.List;

import static org.springframework.boot.SpringApplication.run;

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
        var ctx = run(ShopApplication.class, args);

        /*var orderService = ctx.getBean(OrderService.class);
        var productService = ctx.getBean(ProductService.class);

        productService.add(PRODUCT1);
        productService.add(PRODUCT2);

        var order = Order.builder()
                .products(List.of(PRODUCT1, PRODUCT2))
                .timestamp(Instant.now())
                .build();

        var order2 = Order.builder()
                .products(List.of(PRODUCT1, PRODUCT2))
                .timestamp(Instant.now())
                .build();

        orderService.add(order);
        orderService.add(order2);*/

    }
}
