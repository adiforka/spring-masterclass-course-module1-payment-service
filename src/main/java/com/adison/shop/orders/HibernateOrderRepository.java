package com.adison.shop.orders;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class HibernateOrderRepository implements OrderRepository{

    private final SessionFactory sessionFactory;

    @Override
    public Order save(Order order) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(order);
        order.setId(id);
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.find(Order.class,id);
        return Optional.ofNullable(order);
    }

    @Override
    public void update(Order order) {
        Session session = sessionFactory.getCurrentSession();
        //check if order exists, and if so, call update on session, passing the update order instance
        if (session.load(Order.class, order.getId()) != null) {
            session.update(order);
        }

    }
}
