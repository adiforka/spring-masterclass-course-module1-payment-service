package com.adison.shop.mails;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.mail.internet.MimeMessage;

@Log
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final JmsTemplate jmsTemplate;
    private final Queue mailQueue;

    @Value("${emailSender}")
    @Setter
    private String sender;

    //sends the message object to the queue
    @SneakyThrows
    public void send(MailMessage mailMessage) {
        jmsTemplate.convertAndSend(mailQueue.getQueueName(), mailMessage);
    }

    @JmsListener(destination = "MailDS")
    @SneakyThrows
    public void onMessage(MailMessage message) {
         var messagePreparator = createMimeMessagePreparator(message);
        mailSender.send(messagePreparator);
        }

    private MimeMessagePreparator createMimeMessagePreparator(MailMessage message) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(sender);
            messageHelper.setTo(message.getRecipient());
            messageHelper.setSubject(message.getSubject());
            messageHelper.setText(message.getText(), true);
        };
    }
}

