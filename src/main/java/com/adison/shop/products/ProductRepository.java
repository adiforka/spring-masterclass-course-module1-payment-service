package com.adison.shop.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
//if we make our base ProductRepository extend this custom interface in addition to the JpaProductRepository,
// it'll have access to both interfaces' methods
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    //cool--returns all product objects whose name contains the argument string (SD's DSL)
    //good for simple queries
    Page<Product> findByNameContaining(String id, PageRequest pageRequest);

    //for more complex queries, use @Query with JPQL. this adds query validation too from SD aww <3
    @Query("select p from Product p where p.type = :type")
    Page<Product> findProductByType(@Param("type") ProductType type, Pageable pageRequest); //exception if pageRequest used

}
