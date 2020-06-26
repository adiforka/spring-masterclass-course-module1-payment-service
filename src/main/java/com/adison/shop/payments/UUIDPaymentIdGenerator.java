package com.adison.shop.payments;

import org.springframework.stereotype.Component;

import java.util.UUID;

//@Component
public class UUIDPaymentIdGenerator implements PaymentIdGenerator {

    public String getNext() {
        return UUID.randomUUID().toString();
    }
}
