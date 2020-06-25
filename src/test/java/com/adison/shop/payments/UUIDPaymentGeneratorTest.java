package com.adison.shop.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UUIDPaymentGeneratorTest {

    private static final String ID_FORMAT = "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}";

    private UUIDPaymentIdGenerator generator;

    @BeforeEach
    void init() {
        generator = new UUIDPaymentIdGenerator();
    }

    @DisplayName("Should generate valid id")
    @Test
    void shouldGenerateValidId() {
        String id = generator.getNext();
        assertTrue(id.matches(ID_FORMAT));
    }
}
