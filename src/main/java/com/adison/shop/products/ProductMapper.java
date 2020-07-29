package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.payments.LocalMoney;
import org.javamoney.moneta.FastMoney;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    default FastMoney toFastMoney(String price) {
        if (price == null) {
            return LocalMoney.of(0);
        }
        return FastMoney.parse(price);
    }

    default String toPrice(FastMoney price) {
        if (price == null) {
            return "";
        }
        return price.toString();
    }

    Product toProduct(ProductDTO productDTO);

    ProductDTO toProductDTO(Product product);

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO> toProductDTOs(List<Product> products);

    PagedResultDTO<ProductDTO> toProductsPageDTO(PagedResult<Product> productsPage);

    //for mapping enums
    @ValueMapping(target = "EBOOK", source = "BOOK")
    @ValueMapping(target = "MUSIC", source = "AUDIO")
    @ValueMapping(target = "VIDEO", source = "VIDEO")
    ProductTypeDTO toProductTypeDTO(ProductType type);

    @ValueMapping(target = "BOOK", source = "EBOOK")
    @ValueMapping(target = "AUDIO", source = "MUSIC")
    @ValueMapping(target = "VIDEO", source = "VIDEO")
    ProductType toProductType(ProductTypeDTO typeDTO);



}
