package com.adison.shop.orders;

import com.adison.shop.common.web.IdDTO;
import com.adison.shop.products.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    private List<IdDTO> products;
}
