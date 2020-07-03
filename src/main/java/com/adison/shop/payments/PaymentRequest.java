package com.adison.shop.payments;

import lombok.Builder;
import lombok.Value;
import org.javamoney.moneta.FastMoney;

//value makes instance of this class immutable, handles some boilerplate
//builder gets us builder pattern functionality
@Value
@Builder
public class PaymentRequest {

    Long id;
    //uses primitive longs in its impl, so supposedly works quicker for money specifically
    FastMoney money;
}
