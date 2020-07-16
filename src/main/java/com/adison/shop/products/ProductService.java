package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.retry.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Retry
    public Product add(Product product) {
        //the exception to test the retryExecutor
        //throw new RuntimeException();
        return productRepository.save(product);
    }

    public PagedResult<Product> getAll(int pageNumber, int pageSize) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        //repackage to out own PagedResult type. good practice in case of future migration from SpringData -- we're
        //not relying directly on SD type result return
        return new PagedResult<>(productPage.getContent(), pageNumber, productPage.getTotalPages());
    }
}
