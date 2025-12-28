package com.springbootproject.catalog_service.web.controllers;

import com.springbootproject.catalog_service.domain.PagedResult;
import com.springbootproject.catalog_service.domain.Product;
import com.springbootproject.catalog_service.domain.ProductEntity;
import com.springbootproject.catalog_service.domain.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService){
        this.productService= productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name="page", defaultValue = "1")int pageNo){
        return productService.getProducts(pageNo);
    }
}
