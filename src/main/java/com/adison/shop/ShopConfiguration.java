package com.adison.shop;

import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.PaymentService;
import com.adison.shop.products.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//switches AspectJ into the Spring configuration
@EnableAspectJAutoProxy
@Configuration
public class ShopConfiguration {

    @Bean
    public ShopService shopService(OrderService orderService,
                                   ProductService productService,
                                   PaymentService paymentService) {
        return new ShopService(orderService, productService, paymentService);
    }
}
