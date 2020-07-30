package com.adison.shop.products;

import com.adison.shop.common.PagedResult;
import com.adison.shop.common.web.PagedResultDTO;
import com.adison.shop.common.web.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping("api/products")
@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UriBuilder uriBuilder = new UriBuilder();

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().build();
        }
        var product = productMapper.toProduct(productDTO);
        var productId = productService.add(product).getId();
        var locationUri = uriBuilder.requestUriWithId(productId);
        return ResponseEntity.created(locationUri).build();
    }

    //how to better differentiate request urls?
    @GetMapping("get-by-name")
    public PagedResultDTO<ProductDTO> getProductsByName(
            @RequestParam String nameFragment,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        PagedResult<Product> productsPage = productService.getByName(nameFragment, pageNumber, pageSize);
        PagedResultDTO<ProductDTO> productsPageDTO = productMapper.toProductsPageDTO(productsPage);
        //hateoas
        productsPageDTO.add(linkTo(methodOn(ProductRestController.class)
                .getProductsByName(nameFragment, pageNumber, pageSize))
                .withSelfRel());
        return productsPageDTO;
    }

    @GetMapping("get-by-type")
    public PagedResultDTO<ProductDTO> getProductsByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        var productsPage = productService.getByType(type, pageNumber, pageSize);
        var productsPageDTO = productMapper.toProductsPageDTO(productsPage);
        //hateoas
        productsPageDTO.add(linkTo(methodOn(ProductRestController.class)
                .getProductsByType(type, pageNumber, pageSize))
                .withSelfRel());
        return productsPageDTO;
    }

    @GetMapping
    public ResponseEntity<PagedResultDTO<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        var productsPage = productService.getAll(pageNumber, pageSize);
        var productsPageDTO = productMapper.toProductsPageDTO(productsPage);
        //hateoas
        productsPageDTO.add(linkTo(methodOn(ProductRestController.class)
                .getAllProducts(pageNumber, pageSize))
                .withSelfRel());
        //using response entity here to remember it's the basic option
        return ResponseEntity.ok(productsPageDTO);
    }
}
