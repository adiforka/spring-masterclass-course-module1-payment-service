package com.adison.shop.legacy;

import com.adison.shop.common.FastMoneyUserType;
import org.javamoney.moneta.FastMoney;

import javax.ejb.Local;

@Local
public interface ExchangeRate {

    double get(FastMoney value);
}
