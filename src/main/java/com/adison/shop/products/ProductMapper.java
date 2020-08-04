package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.FastMoneyMapper;
import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.payments.LocalMoney;
import org.javamoney.moneta.FastMoney;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring", uses = FastMoneyMapper.class)
public interface ProductMapper {

    Product toProduct(ProductDTO productDTO);

    ProductDTO toProductDTO(Product product);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO> toProductDTOs(List<Product> products);

    @IterableMapping(elementTargetType = Product.class)
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
