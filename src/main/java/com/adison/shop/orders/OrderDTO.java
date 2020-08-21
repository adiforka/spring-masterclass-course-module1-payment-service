package com.adison.shop.orders;

import com.adison.shop.common.web.IdDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.List;

@Data
public class OrderDTO {

    private Long id;
    @NotEmpty
    private List<IdDTO> products;
    private Instant timePlaced;
}
