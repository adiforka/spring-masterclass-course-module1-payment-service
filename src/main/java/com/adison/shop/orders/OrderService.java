package com.adison.shop.orders;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.validator.Validate;
import com.adison.shop.payments.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final JavaMailSender mailSender;

    public Order add(@Validate(exceptionType = InvalidOrderException.class) Order order) {
        order.setTimestamp(Instant.now());
        order.setPayment(Payment.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .money(order.getTotalPrice())
                .build());
        // move to aspect
        sendEmail();
        return orderRepository.save(order);
    }

    public Order getById(Long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

    private void sendEmail() {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            var messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("adiforka@gmail.com");
            messageHelper.setTo("the.adicide@gmail.com");
            messageHelper.setSubject("New order");
            messageHelper.setText("New order has been placed", true);
        };
        mailSender.send(messagePreparator);
    }

    public void update(Order order) {
        // JpaRepository in SD uses save to update rows on managed entities (when it flushed changes)
        // in db if an element already exists
        orderRepository.save(order);
    }

    public PagedResult<Order> getAll(int pageNumber, int pageSize) {
        var orderPage = orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return new PagedResult<>(orderPage.getContent(), pageNumber, orderPage.getTotalPages());
    }

}
