package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.payments.LocalMoney;
import org.javamoney.moneta.FastMoney;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface ProductMapper {


    Product toProduct(ProductDTO productDTO);

    default FastMoney toFastMoney(String amount) {
        var currencyUnit = Monetary.getCurrency(Locale.getDefault());
        return FastMoney.of(Double.parseDouble(amount), currencyUnit);
    }

    ProductDTO toProductDTO(Product product);

    default String toAmount(FastMoney money) {
        return money.getNumber().toString();
    }

    @IterableMapping(elementTargetType = ProductDTO.class)
    List<ProductDTO> toProductDTOs(List<Product> products);

    PagedResultDTO<ProductDTO> toProductsPageDTO(PagedResult<Product> productPagedResult);

}
