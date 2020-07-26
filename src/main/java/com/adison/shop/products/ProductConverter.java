package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Service;

import javax.money.Monetary;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ProductConverter {

    public Product toProduct(ProductDTO productDTO) {
        //prep
        var currencyUnit = Monetary.getCurrency(Locale.getDefault());
        var amount = Double.parseDouble(productDTO.getAmount());
        var price = FastMoney.of(amount, currencyUnit);
        var type = ProductType.valueOf(productDTO.getType());

        //conversion
        var product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(price);
        product.setType(type);
        return product;
    }

    public ProductDTO toProductDTO(Product product) {
        var productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setType(product.getType().toString());
        productDTO.setAmount(product.getPrice().getNumber().toString());
        return productDTO;
    }

    public PagedResultDTO<ProductDTO> toProductsPageDTO(PagedResult<Product> productsPage) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product p : productsPage.getData()) {
            productDTOs.add(toProductDTO(p));
        }
        return new PagedResultDTO<>(
                productDTOs,
                productsPage.getPageNumber(),
                productsPage.getTotalPages()
        );
    }
}
