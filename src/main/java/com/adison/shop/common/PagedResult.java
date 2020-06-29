package com.adison.shop.common;

import lombok.Value;

import java.util.List;

//no need for access modifiers, @Value makes objects immutable,
//setting fields to private final, not providing setters etc.
@Value
public class PagedResult<T> {

    List<T> data;
    int pageNumber;
    int totalPages;
}
