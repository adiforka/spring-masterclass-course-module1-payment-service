package com.adison.shop.legacy;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "ShopDS")
})
@Log
public class MessageService implements MessageListener {

    @SneakyThrows
    @Override
    // caught exception with @SneakyThrows with Lombok (will be logged to console)
    public void onMessage(Message message) {
        String text = message.getBody(String.class);
        log.info("Processing new message: " + text);
    }
}
