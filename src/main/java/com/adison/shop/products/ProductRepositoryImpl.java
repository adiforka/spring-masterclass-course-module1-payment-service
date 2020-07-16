package com.adison.shop.products;

import lombok.Setter;
import org.hibernate.Session;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import java.util.Optional;

//this class name matters: it's what Spring Data looks for (impl). Can be changed globally
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    //example. could do this with JpaRepository from SD and dynamic finders
    @Override
    public Optional<Product> findByDescription(String description) {
        Optional<Product> result;
        try {
            result = Optional.of(entityManager.createQuery(
                    "select p from Product p where p.description = :description", Product.class)
                    .setParameter("description", description)
                    .getSingleResult());
        } catch (NoResultException e) {
            result = Optional.empty();
        }
        return result;
    }
}
