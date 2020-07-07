package com.adison.shop;

import com.adison.shop.orders.Order;
import com.adison.shop.payments.LocalMoney;
import com.adison.shop.products.Product;
import com.adison.shop.products.ProductType;
import lombok.extern.java.Log;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collections;
import java.util.List;

@Log
public class Application {
    //tell the container where to look for configuration for the app
    private static final Product VIDEO_PRODUCT = Product.builder()
            .name("Rambo: First Blood")
            .description("80s action")
            .productType(ProductType.VIDEO)
            .price(LocalMoney.of(19.99))
            .build();

    private static final Product BOOK_PRODUCT = Product.builder()
            .name("50 Shades of Gray")
            .description("world-class literature")
            .productType(ProductType.BOOK)
            .price(LocalMoney.of(9.99))
            .build();

    private static final String BASE_PACKAGE = "com.adison.shop";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BASE_PACKAGE);

        try (ctx) {
            var shopService = ctx.getBean(ShopService.class);

            shopService.addProduct(VIDEO_PRODUCT);
            shopService.addProduct(BOOK_PRODUCT);
            log.info(shopService.listProducts(0, 20).toString());

            var order = new Order(Collections.emptyList());
            //you give the order an id when you place it (the magic of the map repo)
            shopService.placeOrder(order);
            var payment = shopService.payForOder(order.getId());
            log.info(payment.toString());
        }
    }
}