package com.adison.shop;

import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.LocalMoney;
import com.adison.shop.products.Product;
import com.adison.shop.products.ProductType;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@Log
@SpringBootApplication
public class ShopApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ShopApplication.class);
    }
}
