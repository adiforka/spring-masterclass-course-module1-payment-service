package com.adison.shop.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class MapPaymentRepositoryTest {

    private static final String PAYMENT_ID = "0";
    private static final Payment PAYMENT = Payment.builder()
            .id(PAYMENT_ID)
            .build();

    @Mock
    private Map<String, Payment> payments;
    private final MapPaymentRepository paymentRepository = new MapPaymentRepository();

    @BeforeEach
    void init() {
        paymentRepository.setPayments(payments);
    }

    @Test
    void shouldSavePaymentInMapUnderCorrectKey() {
        paymentRepository.save(PAYMENT);
        verify(payments).put(PAYMENT_ID, PAYMENT);
    }
}
