package com.adison.shop.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //cool--returns all product objects whose name contains the argument string (SD's DSL)
    //good for simple queries
    List<Product> findByNameContaining(String id);

    //for more complex queries, use @Query with JPQL. this adds query validation too from SD aww <3
    @Query("select p from Product p where p.type = :type")
    List<Product> findProductByType(@Param("type") ProductType type);

}
