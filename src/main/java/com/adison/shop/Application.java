package com.adison.shop;

import com.adison.shop.orders.Order;
import com.adison.shop.payments.LocalMoney;
import com.adison.shop.products.Product;
import com.adison.shop.products.ProductRepository;
import com.adison.shop.products.ProductType;
import com.adison.shop.users.User;
import lombok.extern.java.Log;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.Optional;

@Log
public class Application {
    //tell the container where to look for configuration for the app
    private static final Product VIDEO_PRODUCT = Product.builder()
            .name("Rambo: First Blood")
            .description("80s action")
            .type(ProductType.VIDEO)
            .price(LocalMoney.of(19.99))
            .build();

    private static final Product BOOK_PRODUCT = Product.builder()
            .name("50 Shades of Gray")
            .description("world-class literature")
            .type(ProductType.BOOK)
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

            var order = new Order(List.of(VIDEO_PRODUCT, BOOK_PRODUCT));
            shopService.placeOrder(order);
            var payment = shopService.payForOrder(order.getId());
            log.info(payment.toString());

        }
    }
}