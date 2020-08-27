package com.adison.shop.payments;

import com.adison.shop.payments.LocalMoney;
import com.adison.shop.payments.Payment;
import com.adison.shop.payments.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.adison.shop.payments.PaymentStatus.*;
import static org.junit.jupiter.api.Assertions.*;

//raise just what you need of your Spring app (just a slice)
@DataJpaTest
@ExtendWith(SpringExtension.class)
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private final Payment firstPayment = Payment.builder()
            .id(UUID.randomUUID().toString())
            .money(LocalMoney.of(99.99))
            .status(CONFIRMED)
            .timestamp(Instant.now())
            .build();

    private final Payment secondPayment = Payment.builder()
            .id(UUID.randomUUID().toString())
            .money(LocalMoney.of(99.99))
            .status(CANCELED)
            .timestamp(Instant.now())
            .build();

    @BeforeEach
    void setUp() {
        testEntityManager.persist(firstPayment);
        testEntityManager.persist(secondPayment);
        testEntityManager.flush();
    }

    //just to show working (out-of-the-box method is already tested by the API-writers)
    @Test
    void shouldReturnAllConfirmedPayments() {
        List<Payment> payments = paymentRepository.findByStatus(CONFIRMED);
        assertTrue(payments.contains(firstPayment));
        assertEquals(1, payments.size());
    }

}