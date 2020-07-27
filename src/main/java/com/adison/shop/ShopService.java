/*
package com.adison.shop;

import com.adison.shop.orders.Order;
import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.Payment;
import com.adison.shop.payments.PaymentRequest;
import com.adison.shop.payments.PaymentService;
import com.adison.shop.payments.PaymentStatus;
import com.adison.shop.products.Product;
import com.adison.shop.products.ProductService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopService {

    private final ProductService productService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    public Order placeOrder(Order order) {
        return orderService.add(order);
    }

    public Payment payForOrder(long orderId) {
        var order = orderService.getById(orderId);
        var paymentRequest = PaymentRequest.builder()
                .money(order.getTotalPrice())
                .build();
        var payment = paymentService.process(paymentRequest);
        order.setPayment(payment);
        orderService.update(order);
        return payment;
    }

}
*/
