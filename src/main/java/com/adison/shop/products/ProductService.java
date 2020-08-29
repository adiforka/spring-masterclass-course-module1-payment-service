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

    //@CacheEvict(cacheNames = "productsNames")
    @Retry
    public Product add(Product product) {
        //the exception to test the retryExecutor
        //throw new RuntimeException();
        return productRepository.save(product);
    }

    //@CacheEvict(cacheNames = "productsNames")
    //refactor?
    public Product update(Product product, Long id) {
        Product productToUpdate = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setType(product.getType());
        //updated product gets force-flushed to db
        productRepository.flush();
        return productToUpdate;
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    //@Cacheable(cacheNames = "productsNames" )
    public PagedResult<Product> getAll(int pageNumber, int pageSize) {
        var productPage = productRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PagedResult<>(productPage.getContent(), pageNumber, productPage.getTotalPages());
    }

    public PagedResult<Product> getByType(String typeString, int pageNumber, int pageSize) {
        var productType = ProductType.valueOf(typeString);
        Page<Product> productPage = productRepository.findProductByType(productType, PageRequest.of(pageNumber, pageSize));
        return new PagedResult<>(productPage.getContent(), pageNumber, productPage.getTotalPages());
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
