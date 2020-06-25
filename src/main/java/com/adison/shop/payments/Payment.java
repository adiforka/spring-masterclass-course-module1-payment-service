package com.adison.shop.payments;

import lombok.Builder;
import lombok.Value;
import org.javamoney.moneta.FastMoney;

import java.time.Instant;

//see payment request on annotation comment
@Value
@Builder
public class Payment {

    String id;
    FastMoney money;
    Instant timestamp;
    //interesting: we're making Payment instances immutable,
    //so how will we be able to update the payment's status?
    PaymentStatus status;
}
