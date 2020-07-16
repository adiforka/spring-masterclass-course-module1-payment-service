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
    public ProductRepository hibernateProductRepository(SessionFactory sessionfactory) {
        return new HibernateProductRepository(sessionfactory);
    }

    @Bean
    public ProductRepository jpaProductRepository() {
        return new JpaProductRepository();
    }

    @Bean
    public ProductService productService(ProductRepository jpaProductRepository) {
        return new ProductService(jpaProductRepository);
    }
}
