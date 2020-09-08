package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.FastMoneyMapper;
import com.adison.shop.common.web.PagedResultDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = FastMoneyMapper.class)
public interface ProductMapper {

    Product toProduct(ProductDTO productDTO);

    ProductDTO toProductDTO(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromParam(Product product, @MappingTarget Product toUpdate);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO> toProductDTOs(List<Product> products);

    @InheritInverseConfiguration
    List<Product> toProducts(List<ProductDTO> productDTOS);

    PagedResultDTO<ProductDTO> toProductsPageDTO(PagedResult<Product> productsPage);

    //for mapping enums
    @ValueMapping(target = "EBOOK", source = "BOOK")
    @ValueMapping(target = "MUSIC", source = "AUDIO")
    @ValueMapping(target = "VIDEO", source = "VIDEO")
    ProductTypeDTO toProductTypeDTO(ProductType productType);

   @InheritInverseConfiguration
    ProductType toProductType(ProductTypeDTO productTypeDTO);
}
