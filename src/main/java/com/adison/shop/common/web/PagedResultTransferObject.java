package com.adison.shop.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

//no need for access modifiers, @Value makes objects immutable,
//setting fields to private final, not providing setters etc.
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResultTransferObject<T> {

    private List<T> data;
    private int pageNumber;
    private int totalPages;
}
