package com.adison.shop.payments;

public interface PaymentService {

    Payment process(PaymentRequest paymentRequest);
}
