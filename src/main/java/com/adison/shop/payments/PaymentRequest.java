package com.adison.shop.payments;

import lombok.Builder;
import lombok.Value;
import org.javamoney.moneta.FastMoney;

@Value
@Builder
public class PaymentRequest {

    Long id;
    FastMoney money;
}
