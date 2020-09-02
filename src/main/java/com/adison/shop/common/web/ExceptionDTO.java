package com.adison.shop.common.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class ExceptionDTO {

    @NonNull
    private String description;
    @NonNull
    private Instant instant;
}

