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

@RequestMapping("api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final UriBuilder uriBuilder;

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(
            @Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseEntity.badRequest().build();
        }
        Product product = productMapper.toProduct(productDTO);
        Long productId = productService.add(product).getId();
        URI locationUri = uriBuilder.requestUriWithId(productId);
        return ResponseEntity.created(locationUri).build();
    }

    //how to better differentiate request urls?
    @GetMapping("getByName")
    public PagedResultDTO<ProductDTO> getProductsByName(
            @RequestParam String nameFragment,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        PagedResult<Product> productsPage = productService.getByName(nameFragment, pageNumber, pageSize);
        return productMapper.toProductTransferObjectPage(productsPage);
    }

    @GetMapping("getByType")
    public PagedResultDTO<ProductDTO> getProductsByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
            ) {
        PagedResult<Product> productsPage = productService.getByType(type, pageNumber, pageSize);
        return productMapper.toProductTransferObjectPage(productsPage);
    }

    @GetMapping
    public PagedResultDTO<ProductDTO> getAllProducts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        PagedResult<Product> productPage = productService.getAll(pageNumber, pageSize);
        return productMapper.toProductTransferObjectPage(productPage);
    }
}
