package com.adison.shop.payments;

import java.util.UUID;

public class UUIDPaymentIdGenerator implements PaymentIdGenerator {

    //random, not sequential
    public String getNext() {
        return UUID.randomUUID().toString();
    }
}
