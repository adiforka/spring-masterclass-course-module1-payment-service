package com.adison.shop.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IncrementalPaymentIdGeneratorTest {

    private static final String ID_FORMAT = "\\d{10}";

    private IncrementalPaymentIdGenerator generator;

    @BeforeEach
    void init() {
        generator = new IncrementalPaymentIdGenerator();
    }

    @DisplayName("Should generate valid id")
    @Test
    void shouldGenerateValidId() {
        String id = generator.getNext();
        assertTrue(id.matches(ID_FORMAT));
    }

    @DisplayName("Should increment id with regard to the id generated directly before this one")
    @Test
    void shouldGenerateIncrementedId() {
        String id1 = generator.getNext();
        String id2 = generator.getNext();
        assertEquals(Long.parseLong(id1) + 1, Long.parseLong(id2));
    }
}
