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
import com.adison.shop.users.User;
import com.adison.shop.users.UserService;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

//for every method in this class, a transactional context aspect is run over it. works also for all methods used
//for delegation for methods in this class. if they throw an Exception, methods here will be rolled back
@Transactional
//higher-level service that uses product, payment, order services
@RequiredArgsConstructor
public class ShopService {

    private final OrderService orderService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final UserService userService;

    public Product addProduct(Product product) {
        return productService.add(product);
    }

    public List<Product> getProductByName(String name) {
        return productService.getByName(name);
    }

    public PagedResult<Product> listProducts(int pageNumber, int pageSize) {
        return productService.getAll(pageNumber, pageSize);
    }

    public User addUser(User user) {
        return userService.add(user);
    }

    public User getUserById(Long id) {
        return userService.getById(id);
    }

    public List<User> getUserByName(String firstName, String lastName) {
        return userService.getByName(firstName, lastName);
    }

    public Order placeOrder(Order order) {
        return orderService.add(order);
    }

    public Payment payForOrder(Long orderId) {
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
