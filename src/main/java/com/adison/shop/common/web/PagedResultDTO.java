package com.adison.shop.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResultDTO<T> extends RepresentationModel<PagedResultDTO<T>> {

    private List<T> data;
    private int pageNumber;
    private int totalPages;
}
