package com.adison.shop.payments;

import com.adison.shop.payments.UUIDPaymentIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UUIDPaymentIdGeneratorTest {

    public static final String ID_FORMAT =
            "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}";

    private final UUIDPaymentIdGenerator generator = new UUIDPaymentIdGenerator();

    @DisplayName("Should generate valid id")
    @Test
    void shouldGenerateValidId() {
        String testId = generator.getNext();
        assertTrue(testId.matches(ID_FORMAT));
    }
}
