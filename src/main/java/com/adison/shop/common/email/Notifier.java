package com.adison.shop.common.email;

import com.adison.shop.mails.MailMessage;
import com.adison.shop.mails.MailService;
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

    //uses mail service to send an email to user on placed order through this aspect
    private final MailService mailService;

    @Pointcut("execution(com.adison.shop.orders.Order com.adison.shop.orders.OrderService.add(..))")
    public void applySendEmail() {}

    @AfterReturning(pointcut = "applySendEmail()", returning = "order")
    public void sendOrderConfirmationEmail(Order order) {
        mailService.send(MailMessage.builder()
        .recipient("\"mmmartus@gmail.com\"")
        .subject("New order")
        .text("New order has been placed. Order id: " + order.getId())
        .build());
        System.out.println("Sending email");
        }
    }
