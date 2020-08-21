package com.adison.shop.legacy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jndi.JndiTemplate;

import javax.jms.ConnectionFactory;
import javax.naming.NamingException;

@EnableJms // from spring-jms
@Configuration
public class LegacyConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() throws NamingException {
        JndiTemplate template = new JndiTemplate();
        return template.lookup("java:/ConnectionFactory", ConnectionFactory.class);
    }

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        var factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("5-10");
        return factory;
    }
}
