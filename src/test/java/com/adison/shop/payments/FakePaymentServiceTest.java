package com.adison.shop.payments;

import org.javamoney.moneta.FastMoney;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FakePaymentServiceTest {

    private static final String PAYMENT_ID = "1";
    private static final FastMoney MONEY = LocalMoney.of(1_000);
    private static final PaymentRequest PAYMENT_REQUEST = PaymentRequest.builder()
            .money(MONEY)
            .build();

    @Mock
    private PaymentIdGenerator generator;
    @Mock
    private PaymentRepository paymentRepository;
    private Payment payment;
    private FakePaymentService fakePaymentService;


    @BeforeEach
    void init() {
        when(generator.getNext()).thenReturn(PAYMENT_ID);
        //when save called on any payment instance, return the same instance--all mocks with when...verify etc.
        when(paymentRepository.save(any(Payment.class))).then(returnsFirstArg());
        fakePaymentService = new FakePaymentService(generator, paymentRepository);
        payment = fakePaymentService.process(PAYMENT_REQUEST);
    }

    @DisplayName("Should assign generated id to created payment")
    @Test
    void shouldAssignGeneratedIdToCreatedPayment() {
        assertEquals(PAYMENT_ID, payment.getId());
    }

    @DisplayName("Should assign money from payment request to created payment")
    @Test
    void shouldAssignMoneyFromPaymentRequestToCreatedPayment() {
        assertEquals(MONEY, payment.getMoney());
    }

    @DisplayName("Should assign a timestamp to payment")
    @Test
    void shouldAssignTimestampToPayment() {
        assertNotNull(payment.getTimestamp());
    }

    @DisplayName("Should set payment status to STARTED on created payment")
    @Test
    void shouldSetPaymentStatusToStartedOnCreatedPayment() {
        assertEquals(PaymentStatus.STARTED, payment.getStatus());
    }

    @DisplayName("Should save created payment")
    @Test
    void shouldSaveCreatedPayment() {
        verify(paymentRepository).save(payment);
    }
}
