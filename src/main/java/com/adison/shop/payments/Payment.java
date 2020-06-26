package com.adison.shop.payments;

import lombok.Builder;
import lombok.Value;
import org.javamoney.moneta.FastMoney;

import java.time.Instant;

@Value
@Builder
public class Payment {

    String id;
    FastMoney money;
    Instant timestamp;
    PaymentStatus status;
}
