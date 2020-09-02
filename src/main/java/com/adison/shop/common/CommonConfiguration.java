package com.adison.shop.common;

import com.adison.shop.common.profiler.Profiler;
import com.adison.shop.common.retry.MethodExecutor;
import com.adison.shop.common.validator.ModelValidator;
import com.adison.shop.common.validator.ValidatorService;
import com.adison.shop.mails.JmsSender;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thymeleaf.TemplateEngine;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.NamingException;
import javax.validation.Validator;

@Configuration
public class CommonConfiguration {

    @Bean
    public Profiler profiler() {
        return new Profiler();
    }

    //spring delivers an implementation of validator from our hibernate validation dependency
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ValidatorService validatorService(Validator validator) {
        return new ValidatorService(validator);
    }

    @Bean
    public ModelValidator modelValidator(ValidatorService validatorService) {
        return new ModelValidator(validatorService);
    }

    //if I don't make this static, the creation of the bean instance will require the creation of a CommonConfiguration
    //instance, which won't be able to be processed by the LoggingBeanPostProcessor. If I make the method static,
    // the CC bean will be created, but only after the LPP is created in the container to log its creation
    //@Bean
    public static BeanPostProcessor beanPostProcessor() {
        return new LoggingBeanPostProcessor();
    }

    @Bean
    public MethodExecutor methodExecutor() {
        var methodExecutor = new MethodExecutor();
        methodExecutor.setAttempts(5);
        return methodExecutor;
    }

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        var defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(connectionFactory);
        defaultJmsListenerContainerFactory.setConcurrency("5-10");
        return defaultJmsListenerContainerFactory;
    }

    @Bean
    ConnectionFactory connectionFactory() throws NamingException {
        var jndiTemplate = new JndiTemplate();
        return jndiTemplate.lookup("java:/ConnectionFactory", ConnectionFactory.class);
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        var cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        return new JmsTemplate(cachingConnectionFactory);
    }

    @Bean
    public JmsSender jmsSender(JmsTemplate jmsTemplate, Queue messageQueue) {
        return new JmsSender(jmsTemplate, messageQueue);
    }

    @Bean
    public TextSource textSource(MessageSource messageSource, TemplateEngine templateEngine) {
        return new TextSource(messageSource, templateEngine);
    }



}
