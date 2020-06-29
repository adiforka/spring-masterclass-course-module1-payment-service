package com.adison.shop.products;

import lombok.Builder;
import lombok.Data;
import org.javamoney.moneta.FastMoney;

@Data
@Builder
public class Product {

    private Long id;
    private String name;
    private String description;
    private FastMoney price;
    private ProductType productType;
}
