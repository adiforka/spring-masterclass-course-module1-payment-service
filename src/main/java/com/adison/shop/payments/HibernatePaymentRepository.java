package com.adison.shop.payments;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@RequiredArgsConstructor
public class HibernatePaymentRepository implements PaymentRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Payment save(Payment payment) {
        //instance tied to the transaction we're currently in (when defined) -- if the transaction fails, the objects
        //tied to current session will not be persisted
        Session session = sessionFactory.getCurrentSession();
        String id = (String) session.save(payment);
        payment.setId(id);
        return payment;
    }
}
