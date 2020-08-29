package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {

    Page<Product> findByDescriptionContaining(String description, Pageable pageRequest);
}
