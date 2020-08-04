package com.adison.shop.products;

import com.adison.shop.common.web.UriBuilder;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {

    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }
}
