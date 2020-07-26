package com.adison.shop.products;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;

@Data
public class ProductDTO extends RepresentationModel<ProductDTO> {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String amount;
    @NotEmpty
    private String type;
}
