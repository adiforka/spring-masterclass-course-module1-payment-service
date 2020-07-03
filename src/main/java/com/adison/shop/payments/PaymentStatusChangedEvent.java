package com.adison.shop.payments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;

public class PaymentStatusChangedEvent extends ApplicationEvent {

    @Getter
    private final Payment payment;

    public PaymentStatusChangedEvent(Object source, Payment payment) {
        super(source);
        this.payment = payment;
    }
}
