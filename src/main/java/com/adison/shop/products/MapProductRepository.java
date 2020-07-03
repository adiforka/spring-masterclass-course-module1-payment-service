package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapProductRepository implements ProductRepository {

    private final Map<Long, Product> products = new HashMap<>();
    private long index = 0;

    @Override
    public Product save(Product product) {
        product.setId(++index);
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public PagedResult<Product> findAll(int pageNumber, int pageSize) {
        //get those remaining results on the last page (ceil will get them even it there's < 5, get it?)
        //being the lowest integer value larger than our result
        //ceiling returns a double (go figure), so cast it back to int
        var totalPages = (int) Math.ceil((double) products.size() / pageSize);
        var data = new ArrayList<>(products.values());
        return new PagedResult<>(data, pageNumber, totalPages);
    }
}
