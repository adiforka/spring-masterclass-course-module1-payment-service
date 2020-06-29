package com.adison.shop.payments;

import java.util.UUID;

@IdGenerator(value = "uuid")
public class UUIDPaymentIdGenerator implements PaymentIdGenerator {

    //random, not sequential
    public String getNext() {
        return UUID.randomUUID().toString();
    }
}
