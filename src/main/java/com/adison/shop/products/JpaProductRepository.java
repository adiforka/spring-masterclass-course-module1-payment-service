package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class JpaProductRepository implements ProductRepository {

    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product save(Product product) {
       entityManager.persist(product);
       entityManager.flush();
       entityManager.refresh(product);
       return product;
    }

    @Override
    public PagedResult<Product> findAll(int pageNumber, int pageSize) {
        List<Product> requestedProducts = entityManager.createNamedQuery(Product.SELECT_PRODUCTS, Product.class)
                .setFirstResult(pageNumber * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        List<Product> allProducts = entityManager
                .createNamedQuery(Product.SELECT_PRODUCTS, Product.class)
                .getResultList();
        int totalPages = (int)Math.ceil((double)allProducts.size() / pageSize);

        return new PagedResult<>(requestedProducts, pageNumber, totalPages);
    }
}
