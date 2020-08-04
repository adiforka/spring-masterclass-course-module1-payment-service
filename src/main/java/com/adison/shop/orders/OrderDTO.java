package com.adison.shop.orders;

import com.adison.shop.common.web.IdDTO;
import com.adison.shop.products.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDTO {

    private List<IdDTO> products;
}
