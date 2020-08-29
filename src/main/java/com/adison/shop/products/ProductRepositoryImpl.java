package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import lombok.Setter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

//this class name matters: it's what Spring Data looks for (impl). Can be changed globally
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    //example. could do this with JpaRepository from SD and dynamic finders
    @Override
    public Page<Product> findByDescriptionContaining(String description, Pageable pageRequest) {
        List<Product> products = entityManager.createQuery(
                "select p from Product p where p.description like concat('%', :description, '%')", Product.class)
                .setParameter("description", description)
                .getResultList();
        return new PageImpl<>(products);
    }
}
