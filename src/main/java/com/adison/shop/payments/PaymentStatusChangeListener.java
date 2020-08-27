package com.adison.shop.payments;

import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Log
public class PaymentStatusChangeListener {

    @EventListener
    public void onPaymentStatusChange(PaymentStatusChangedEvent statusChangedEvent) {
        log.info("Status changed on payment " + statusChangedEvent.getPayment());
    }
}
