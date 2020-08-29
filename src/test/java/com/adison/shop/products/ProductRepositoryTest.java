package com.adison.shop.products;

import com.adison.shop.payments.LocalMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private final Product product1 = Product.builder()
            .name("ESP Eclipse II")
            .description("Single cutaway solid-body electric guitar")
            .price(LocalMoney.of(1999.99))
            .type(ProductType.AUDIO)
            .build();

    private final Product product2 = Product.builder()
            .name("Gibson Les Paul Standard 2020")
            .description("Single cutaway solid-body electric guitar")
            .price(LocalMoney.of(2499.99))
            .type(ProductType.AUDIO)
            .build();

    @BeforeEach
    void setUp() {
        testEntityManager.persist(product1);
        testEntityManager.persist(product2);
        testEntityManager.flush();
    }

    @Test
    void shouldRetrieveProductByDescriptionElement() {
        //given
        Page<Product> expectedProducts = new PageImpl<>(List.of(product1, product2));
        //when
        Page<Product> retrievedProducts = productRepository.findByDescriptionContaining("Single cut", null);
        //then
        assertEquals(expectedProducts, retrievedProducts);
    }

    @Test
    void shouldRetrieveProductsByType() {
        //given
        Page<Product> expectedProducts = new PageImpl<>(List.of(product1, product2));
        //when
        Page<Product> retrievedProducts = productRepository.findProductByType(ProductType.AUDIO, null);
        //then
        assertEquals(expectedProducts, retrievedProducts);
    }

}

