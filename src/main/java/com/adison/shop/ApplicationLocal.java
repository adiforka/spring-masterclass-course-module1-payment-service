package com.adison.shop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationLocal {

    private static final String BASE_PACKAGES = "com.adison.shop";

    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(BASE_PACKAGES);

        try (ctx) {
            var shopService = ctx.getBean(ShopService.class);


        }
    }


}
