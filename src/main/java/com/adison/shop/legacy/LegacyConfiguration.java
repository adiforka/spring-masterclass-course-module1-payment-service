package com.adison.shop.legacy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.NamingException;
import javax.sql.rowset.CachedRowSet;

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

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        // simple wrap
        var cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        return new JmsTemplate(cachingConnectionFactory);
    }

    @Bean
    public Queue messageQueue() throws NamingException {
        var template = new JndiTemplate();
        return template.lookup("jboss/exported/jms/queue/Shop", Queue.class);
    }

    // there already is a Spring bean called jmsSender
    @Bean
    public JmsSender messageSender(JmsTemplate jmsTemplate, Queue messageQueue) {
        return new JmsSender(jmsTemplate, messageQueue);
    }
}
