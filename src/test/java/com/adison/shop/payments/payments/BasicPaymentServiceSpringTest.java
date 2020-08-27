package com.adison.shop.payments.payments;

import com.adison.shop.payments.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BasicPaymentServiceSpringTest {

    private static final String PAYMENT_ID = "1";
    private static final PaymentRequest PAYMENT_REQUEST = PaymentRequest.builder()
            .money(LocalMoney.of(10_000))
            .build();

    @MockBean
    private PaymentIdGenerator paymentIdGenerator;
    @MockBean
    private PaymentRepository paymentRepository;
    @Autowired
    private PaymentService paymentService;
    private Payment payment;

    @TestConfiguration
    static class TestConfig {

        @Bean
        public PaymentService paymentService(PaymentIdGenerator incrementalPaymentIdGenerator,
                                             PaymentRepository paymentRepository) {
            return new BasicPaymentService(incrementalPaymentIdGenerator, paymentRepository);
        }
    }

    @BeforeEach
    void setup() {
        when(paymentIdGenerator.getNext()).thenReturn(PAYMENT_ID);
        when(paymentRepository.save(any(Payment.class))).then(returnsFirstArg());
        //the tested method is called here, unique call for every test case
        payment = paymentService.process(PAYMENT_REQUEST);
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
