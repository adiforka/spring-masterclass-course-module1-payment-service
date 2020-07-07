package com.adison.shop.common;

import com.adison.shop.common.profiler.Profiler;
import com.adison.shop.common.retry.RetryExecutor;
import com.adison.shop.common.validator.ModelValidator;
import com.adison.shop.common.validator.ValidatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public RetryExecutor retryExecutor() {
        return new RetryExecutor();
    }
}
