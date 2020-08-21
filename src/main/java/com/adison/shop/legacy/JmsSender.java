package com.adison.shop.legacy;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

@RequiredArgsConstructor
public class JmsSender {

    private final JmsTemplate jmsTemplate;
    private final Queue queue;

    public void send(String text) {
        jmsTemplate.send(queue, session -> session.createTextMessage(text));
    }
}
