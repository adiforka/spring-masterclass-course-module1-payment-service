package com.adison.shop.payments;

import org.javamoney.moneta.FastMoney;
import org.springframework.cglib.core.Local;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Arrays;
import java.util.Locale;

//utility class --> not to be instantiated. all static methods
public class LocalMoney {

    public static FastMoney of(Number number) {
        return FastMoney.of(number, getCurrencyUnit());
    }

    public static FastMoney zero() {
        return FastMoney.zero(getCurrencyUnit());
    }

    private static CurrencyUnit getCurrencyUnit() {
        var locale = Locale.getDefault();
        return Monetary.getCurrency(locale);
    }
}