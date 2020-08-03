package com.adison.shop.products;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;

@Data
public class ProductDTO extends RepresentationModel<ProductDTO> {

    @NotEmpty
    private String name;
    @Length(min = 3, max = 255)
    private String description;
    private String price;
    private ProductTypeDTO type;
}
