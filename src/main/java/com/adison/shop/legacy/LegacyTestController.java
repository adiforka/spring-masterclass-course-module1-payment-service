package com.adison.shop.legacy;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NamingException;

@RestController
@RequiredArgsConstructor
public class LegacyTestController {

    // Spring's proxy letting us access remote components and ask to have them injected into our code
    // the proxy will implement the same interface as out desired target service
    private final JndiTemplate jndiTemplate = new JndiTemplate();

    @GetMapping("${apiPrefix}/exchange-rate")
    public double getExchangeRate(@RequestParam String value) throws NamingException {
        ExchangeRate exchangeRate = jndiTemplate.lookup(
                "java:global/shop-0.0.1-SNAPSHOT/BasicExchangeRate",
                ExchangeRate.class);
        return exchangeRate.get(FastMoney.parse(value));
    }
}
