package com.adison.shop.payments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BasicPaymentServiceTest {

    private static final String PAYMENT_ID = "1";
    private static final PaymentRequest PAYMENT_REQUEST = PaymentRequest.builder()
            .money(LocalMoney.of(10_000))
            .build();

    @Mock
    private PaymentIdGenerator idGenerator;
    @Mock
    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void setup() {
        BasicPaymentService service = new BasicPaymentService(idGenerator, paymentRepository);
        when(idGenerator.getNext()).thenReturn(PAYMENT_ID);
        when(paymentRepository.save(any(Payment.class))).then(returnsFirstArg());
        //the tested method is called here, unique call for every test case
        payment = service.process(PAYMENT_REQUEST);
    }

    @DisplayName("Should assign generated id to created payment")
    @Test
    void shouldAssignGeneratedIdToCreatedPayment() {
        assertEquals(PAYMENT_ID, payment.getId());
    }

    @DisplayName("Should assign money from payment request to created payment")
    @Test
    void shouldAssignMoneyFromPaymentRequestToCreatedPayment() {
        assertEquals(PAYMENT_REQUEST.getMoney(), payment.getMoney());
    }

    @DisplayName("Should assign a timestamp to payment")
    @Test
    void shouldSetTimestampOnCreatedPayment() {
        assertNotNull(payment.getTimestamp());
    }

    @DisplayName("Should set payment status to STARTED on created payment")
    @Test
    void shouldSetStatusOnCreatedPaymentToStarted() {
        assertEquals(PaymentStatus.STARTED, payment.getStatus());
    }

    @DisplayName("Should call save passing in created payment")
    @Test
    void shouldCallSavePassingInCreatedPayment() {
        verify(paymentRepository).save(any());
    }

}
