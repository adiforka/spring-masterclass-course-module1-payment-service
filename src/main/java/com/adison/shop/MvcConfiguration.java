package com.adison.shop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan(basePackages = "com.adison.shop")
@EnableWebMvc
@Configuration
//WebMvc has lots of default methods we can use (or override)
public class MvcConfiguration implements WebMvcConfigurer {
}
