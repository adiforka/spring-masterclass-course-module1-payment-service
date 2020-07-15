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
                //named queries better because validated at start and also optimized/partially parsed
                //adding return type lets us get Products in the result set from Hibernate without needing to cast later
                .createNamedQuery(Product.SELECT_PRODUCTS, Product.class)
                //this sets the first result you want based on index at however many pages or what size come before this one
                .setFirstResult(pageNumber * pageSize)
                //the last result returned
                .setMaxResults(pageSize)
                //terminal--causes the whole chain to be executed. we actually don't get all the products back, but just
                //the part requested by the pg.no. and size --
                .getResultList();
        //should get total pages for the PagedResult constructor (supposedly SD will do this automatically)
        List<Product> allProducts = sessionFactory.getCurrentSession().createNamedQuery(Product.SELECT_PRODUCTS, Product.class)
                .getResultList();
        var totalPages = (int)Math.ceil((double)allProducts.size() / pageSize);
        return new PagedResult<>(requestedProducts, pageNumber, totalPages);
    }
}
