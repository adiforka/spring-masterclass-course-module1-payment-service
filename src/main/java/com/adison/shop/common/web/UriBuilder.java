package com.adison.shop.common.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriBuilder {

    //using Spring to get current request URI and add the id of added object to it
    //object, cause different types passed
    public URI requestUriWithId(Object id) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
