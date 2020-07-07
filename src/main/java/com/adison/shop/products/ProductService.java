package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.retry.Retry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Retry
    public Product add(Product product) {
        throw new RuntimeException();
        //return productRepository.save(product);
    }

    public PagedResult<Product> getAll(int pageNumber, int pageSize) {
        return productRepository.findAll(pageNumber, pageSize);
    }
}
