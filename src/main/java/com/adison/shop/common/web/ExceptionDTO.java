package com.adison.shop.common.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionDTO {

    @NonNull
    private String description;
    //could have a timestamp here
}
