package com.adison.shop.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepositoryCustom {

    Optional<Product> findByDescription(String description);
}
