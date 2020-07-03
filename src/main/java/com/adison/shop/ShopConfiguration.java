package com.adison.shop;

import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.PaymentService;
import com.adison.shop.products.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

//switches AspectJ into the Spring config
@EnableAspectJAutoProxy
@Configuration
public class ShopConfiguration {

    @Bean
    public ShopService shopService(OrderService orderService,
                                   ProductService productService,
                                   PaymentService paymentService) {
        return new ShopService(orderService, productService, paymentService);
    }

    //configuration for internationalization--adding a message source component to Spring and configuring it
    //this component will have to be injected wherever you want to use it
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "messages_pl_PL");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        return messageSource;
    }
}
