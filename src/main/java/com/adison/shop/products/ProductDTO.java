package com.adison.shop.products;

import lombok.Data;
import org.javamoney.moneta.FastMoney;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;

@Data
public class ProductDTO extends RepresentationModel<ProductDTO> {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private FastMoney price;
    @NotEmpty
    private ProductType type;
}
