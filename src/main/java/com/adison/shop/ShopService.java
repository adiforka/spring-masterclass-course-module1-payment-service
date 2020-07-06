package com.adison.shop;

import com.adison.shop.common.PagedResult;
import com.adison.shop.orders.Order;
import com.adison.shop.orders.OrderService;
import com.adison.shop.payments.IncrementalPaymentIdGenerator;
import com.adison.shop.payments.Payment;
import com.adison.shop.payments.PaymentRequest;
import com.adison.shop.payments.PaymentService;
import com.adison.shop.products.Product;
import com.adison.shop.products.ProductService;
import lombok.RequiredArgsConstructor;

//higher-level service that uses product, payment, order services
@RequiredArgsConstructor
public class ShopService {

    private final OrderService orderService;
    private final ProductService productService;
    private final PaymentService paymentService;

    public Product addProduct(Product product) {
        return productService.add(product);
    }

    public PagedResult<Product> listProducts(int pageNumber, int pageSize) {
        return productService.getAll(pageNumber, pageSize);
    }

    public Order placeOrder(Order order) {
        return orderService.add(order);
    }

    public Payment payForOder(Long orderId) throws Exception {
        var order = orderService.getById(orderId);
        var paymentRequest = PaymentRequest.builder()
                .id(Long.valueOf(new IncrementalPaymentIdGenerator().getNext()))
                .money(order.getTotalPrice())
                .build();
        var payment = paymentService.process(paymentRequest);
        //set payment (containing status) on order and update the order in the order map
        order.setPayment(payment);
        orderService.update(order);
        return payment;
    }




}
