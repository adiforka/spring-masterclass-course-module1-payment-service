package com.adison.shop.orders;

import com.adison.shop.products.ProductDTO;

import java.sql.Timestamp;
import java.util.List;

public class OrderDTO {

    private List<ProductDTO> products;
    private Timestamp timestamp;
}
