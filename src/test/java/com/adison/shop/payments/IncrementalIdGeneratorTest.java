package com.adison.shop.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IncrementalIdGeneratorTest {

    public static final String ID_FORMAT = "\\d{10}";

    private final IncrementalPaymentIdGenerator generator = new IncrementalPaymentIdGenerator();

    @Test
    void shouldGenerateValidId() {
        String testId = generator.getNext();
        assertTrue(testId.matches(ID_FORMAT));
    }

    @Test
    void shouldGenerateIdIncrementedByOneForConsecutiveCalls() {
        long testId1Value = Long.parseLong(generator.getNext());
        long testId2Value = Long.parseLong(generator.getNext());

        assertEquals(testId1Value + 1, testId2Value);
    }
}

