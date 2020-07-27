/*package com.adison.shop;

import com.adison.shop.orders.Order;
import com.adison.shop.payments.LocalMoney;
import com.adison.shop.payments.PaymentConsoleLogger;
import com.adison.shop.products.Product;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ApplicationLocal {

    private static final Product PRODUCT1 = Product.builder()
            .name("Setup done")
            .description("Booyah")
            .price(LocalMoney.of(83.23))
            .build();

    private static final Product PRODUCT2 = Product.builder()
            .name("in progressing business")
            .description("Fooyah")
            .price(LocalMoney.of(121.12))
            .build();

    private static final String BASE_PACKAGES = "com.adison.shop";

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BASE_PACKAGES);

        try (ctx) {
            var shopService = ctx.getBean(ShopService.class);
            var order = new Order(List.of(PRODUCT1, PRODUCT2));
            var placedOrder = shopService.placeOrder(order);
            var payment = shopService.payForOrder(placedOrder.getId());

        }
    }


}*/
