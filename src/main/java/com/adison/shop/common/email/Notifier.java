package com.adison.shop.common.email;

import com.adison.shop.orders.Order;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Aspect
@RequiredArgsConstructor
public class Notifier {

    private final JavaMailSender mailSender;

    @Pointcut("execution(com.adison.shop.orders.Order com.adison.shop.orders.OrderService.add(..))")
    public void applySendEmail() {}

    @AfterReturning(pointcut = "applySendEmail()", returning = "order")
    public void sendOrderConfirmationEmail(Order order) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            var messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("adiforka@gmail.com");
            messageHelper.setTo("mmmartus@gmail.com");
            messageHelper.setSubject("New order");
            messageHelper.setText("New order has been placed. Order id: " + order.getId(),
                    true);
        };
        mailSender.send(messagePreparator);
    }


}
