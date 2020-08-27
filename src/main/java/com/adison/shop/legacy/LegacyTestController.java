package com.adison.shop.legacy;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.FastMoney;
import org.springframework.http.ResponseEntity;
import org.springframework.jndi.JndiTemplate;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;

@RequestMapping("${apiPrefix}")
@RestController
@RequiredArgsConstructor
public class LegacyTestController {

    // Spring's proxy letting us access remote components and ask to have them injected into our code
    // the proxy will implement the same interface as out desired target service
    private final JndiTemplate jndiTemplate = new JndiTemplate();
    private final JmsSender jmsSender;

    @GetMapping("${apiPrefix}/exchange-rate")
    public double getExchangeRate(@RequestParam String value) throws NamingException {
        ExchangeRate exchangeRate = jndiTemplate.lookup(
                "java:global/BasicExchangeRate",
                ExchangeRate.class);
        return exchangeRate.get(FastMoney.parse(value));
    }

    @PostMapping("messages")
    public ResponseEntity<Void> sendMessage(@RequestBody String text) {
        jmsSender.send(text);
        return ResponseEntity.accepted().build();
    }
}
