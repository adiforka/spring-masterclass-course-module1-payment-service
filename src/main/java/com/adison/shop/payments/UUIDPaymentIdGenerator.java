package com.adison.shop.payments;

import java.util.UUID;

//use UUID to generate ids for payments
public class UUIDPaymentIdGenerator {

    //random, not sequential
    public String getNext() {
        return UUID.randomUUID().toString();
    }
}
