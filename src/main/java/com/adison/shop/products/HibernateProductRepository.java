package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@RequiredArgsConstructor
public class HibernateProductRepository implements ProductRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Product save(Product product) {
       Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(product);
        product.setId(id);
        return product;
    }

    @Override
    public PagedResult<Product> findAll(int pageNumber, int pageSize) {
        List<Product> requestedProducts =  sessionFactory.getCurrentSession()
                .createNamedQuery(Product.SELECT_PRODUCTS, Product.class)
                .setFirstResult(pageNumber * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        List<Product> allProducts = sessionFactory
                .getCurrentSession()
                .createNamedQuery(Product.SELECT_PRODUCTS, Product.class)
                .getResultList();
        var totalPages = (int)Math.ceil((double)allProducts.size() / pageSize);

        return new PagedResult<>(requestedProducts, pageNumber, totalPages);
    }
}
