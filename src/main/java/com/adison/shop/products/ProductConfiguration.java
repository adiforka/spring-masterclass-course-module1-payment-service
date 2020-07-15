package com.adison.shop.products;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {

    @Bean
    public ProductRepository mapProductRepository() {
        return new MapProductRepository();
    }

    @Bean
    public HibernateProductRepository hibernateProductRepository(SessionFactory sessionfactory) {
        return new HibernateProductRepository(sessionfactory);
    }

    @Bean
    public ProductService productService(ProductRepository hibernateProductRepository) {
        return new ProductService(hibernateProductRepository);
    }
}
