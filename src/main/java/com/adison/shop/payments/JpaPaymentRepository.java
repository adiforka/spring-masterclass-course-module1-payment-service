package com.adison.shop.payments;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaPaymentRepository implements PaymentRepository{

    //unlike with Hibernate, with JPA I don't need to get a EMfactory and pull an instance out of it. Spring will
    //inject an EM instance directly based on the annotation below (EntityManager in JPA is like Session in Hibernate)
    @Setter
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Payment save(Payment payment) {
       entityManager.persist(payment);
       //enforce persisting with flush in contexts requiring synchronization (ORMs will try to execute as late as possible)
        entityManager.flush();
        //refresh entity based on current db state (this is awesome--cause we need to return the persisted instance of payment
        //and persist has void type return
        entityManager.refresh(payment);
        return payment;
    }
}
