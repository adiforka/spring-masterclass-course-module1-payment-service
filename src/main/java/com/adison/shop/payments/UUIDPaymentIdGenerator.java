package com.adison.shop.payments;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDPaymentIdGenerator implements PaymentIdGenerator {

    //random, not sequential
    public String getNext() {
        return UUID.randomUUID().toString();
    }
}
