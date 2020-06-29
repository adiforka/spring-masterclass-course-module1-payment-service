package com.adison.shop.products;

import com.adison.shop.common.PagedResult;

public interface ProductRepository {

    Product save(Product product);

    PagedResult<Product> findAll(int pageNumber, int pageSize);
}
