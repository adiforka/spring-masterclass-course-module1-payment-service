package com.adison.shop.products;

import com.adison.shop.payments.LocalMoney;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsArgAt;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static final Long PRODUCT1_ID = 666L;

    @Mock
    private ProductRepository productRepository;
    private ProductService productService;

    private static final Product product1 = Product.builder()
            .id(PRODUCT1_ID)
            .name("ESP Eclipse II")
            .description("Single cutaway solid-body electric guitar")
            .price(LocalMoney.of(1999.99))
            .type(ProductType.AUDIO)
            .build();

    private static final Product product2 = Product.builder()
            .name("Gibson Les Paul Standard 2020")
            .description("Single cutaway solid-body electric guitar")
            .price(LocalMoney.of(2499.99))
            .type(ProductType.AUDIO)
            .build();

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    void shouldAddProduct() {
        //given
        when(productRepository.save(any(Product.class))).then(returnsFirstArg());
        //when
        Product addedProduct = productService.add(product1);
        //then
        assertEquals(product1, addedProduct);
    }

    @Test
    void shouldUpdateProductWhenValidProductAndIdGiven() {
        //given
        when(productRepository.save(any(Product.class))).then(returnsFirstArg());
        when(productRepository.findById(PRODUCT1_ID)).thenReturn(Optional.ofNullable(product1));
        productService.add(product1);
        //when
        Product updatedProduct = productService.update(product2, PRODUCT1_ID);
        //then (updates added product as an entity object, so don't need to check against product2, but that's my ref
        assertNotNull(updatedProduct.getId());
        assertEquals(product2.getName(), updatedProduct.getName());
        assertEquals(product2.getPrice(), updatedProduct.getPrice());
        assertEquals(product2.getDescription(), updatedProduct.getDescription());
        verify(productRepository, atLeast(1)).flush();
        verify(productRepository, atMost(1)).findById(anyLong());
    }
}
