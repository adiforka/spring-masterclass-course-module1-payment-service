package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "name", target = "name")
    ProductDTO toProductTransferObject(Product product);

    @Mapping(source = "name", target = "name")
    Product toProduct(ProductDTO productDTO);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO> toProductTransferObjects(List<Product> products);

    PagedResultDTO<ProductDTO> toProductTransferObjectPage(PagedResult<Product> productPagedResult);
}
