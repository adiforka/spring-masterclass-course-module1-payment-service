package com.adison.shop.common;

import com.adison.shop.common.profiler.Profiler;
import com.adison.shop.common.retry.MethodExecutor;
import com.adison.shop.common.validator.ModelValidator;
import com.adison.shop.common.validator.ValidatorService;
import com.adison.shop.common.web.UriBuilder;
import com.adison.shop.products.ProductRestController;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

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
}
