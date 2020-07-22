package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.retry.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.List;

@Log
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //looks like this makes sure to read products from outside the cache, but if I look for a product that was
    //already cached, I don't have the getByName() (below) execution log
    @CacheEvict(cacheNames = "productsnames")
    @Retry
    public Product add(Product product) {
        //the exception to test the retryExecutor
        //throw new RuntimeException();
        return productRepository.save(product);
    }
    //when results of first call are cached, this won't be called again until cache is emptied
    @Cacheable(cacheNames = "productsNames")
    public PagedResult<Product> getByName(String name, int pageNumber, int pageSize) {
        Page<Product> productPage = productRepository.findByNameContaining(name, PageRequest.of(pageNumber, pageSize));
        return new PagedResult<>(productPage.getContent(), pageNumber, productPage.getTotalPages());
    }

    public PagedResult<Product> getByType(String typeString, int pageNumber, int pageSize) {
        //convert string type object to Product type
        ProductType type = ProductType.valueOf(typeString);
        Page<Product> productPage = productRepository.findProductByType(type, PageRequest.of(pageNumber, pageSize));
        return new PagedResult<>(productPage.getContent(), pageNumber, productPage.getTotalPages());
    }

    public PagedResult<Product> getAll(int pageNumber, int pageSize) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        //repackage to out own PagedResult type. good practice in case of future migration from SpringData -- we're
        //not relying directly on SD type result return
        return new PagedResult<>(productPage.getContent(), pageNumber, productPage.getTotalPages());
    }
}
