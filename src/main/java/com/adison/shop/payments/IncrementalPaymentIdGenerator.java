package com.adison.shop.payments;

import lombok.Setter;
import org.springframework.stereotype.Component;

//makes the class a Spring-managed component (bean, ofc)
//@Component
public class IncrementalPaymentIdGenerator implements PaymentIdGenerator {

    private static final String ID_FORMAT = "%010d";

    @Setter
    //I think Lombok initializes this to 0 (because the setter is not called anywhere)
    private long index;

    @Override
    public String getNext() {
        return String.format(ID_FORMAT, ++index);
    }
}
