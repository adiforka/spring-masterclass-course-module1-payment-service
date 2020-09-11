package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.retry.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

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
    public Product update(Product source, Long id) {
        Product target = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        updateInstanceDetails(source, target);
        productRepository.flush();
        return target;
    }

    private void updateInstanceDetails(Product source, Product target) {
        target.setName(source.getName());
        target.setPrice(source.getPrice());
        target.setType(source.getType());
        target.setDescription(source.getDescription());
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    //@Cacheable(cacheNames = "productsNames")
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
