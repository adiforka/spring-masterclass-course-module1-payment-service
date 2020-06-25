package com.adison.shop.payments;

//model classes to handle payments (#1)
import lombok.Builder;
import lombok.Value;
import org.javamoney.moneta.FastMoney;

//value makes instance of this class immutable, handles some boilerplate
//builder gets us builder pattern functionality
@Value
@Builder
public class PaymentRequest {

    //still want to use private (force of habit, makes reading my own code easier)
    private Long id;
    //uses primitive longs in its impl, so supposedly works quicker for money specifically
    private FastMoney money;
}
