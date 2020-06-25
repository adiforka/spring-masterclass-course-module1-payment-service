package com.adison.shop.payments;

import java.io.Serializable;
import java.util.UUID;

//use UUID to generate ids for payments
public class UUIDPaymentIdGenerator implements PaymentIdGenerator {

    //random, not sequential
    public String getNext() {
        return UUID.randomUUID().toString();
    }
}
