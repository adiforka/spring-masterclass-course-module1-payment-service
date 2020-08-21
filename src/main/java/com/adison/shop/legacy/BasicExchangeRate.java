package com.adison.shop.legacy;

import org.javamoney.moneta.FastMoney;

import javax.ejb.Stateless;
import java.util.Random;

@Stateless
public class BasicExchangeRate implements ExchangeRate {

    private final Random random = new Random();

    @Override
    public double get(FastMoney value) {
        return random.nextInt(10) / (double) 10;
    }
}
