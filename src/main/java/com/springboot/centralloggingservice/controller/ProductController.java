package com.springboot.centralloggingservice.controller;

import com.springboot.centralloggingservice.dto.Product;
import com.springboot.centralloggingservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    Product saveProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @GetMapping("/product")
    List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/product/{id}")
    Product getProducts(@PathVariable("id") String id) throws Exception {
        return productService.getProductById(id);
    }

}
