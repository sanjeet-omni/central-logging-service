package com.springboot.centralloggingservice.service;

import com.springboot.centralloggingservice.dto.ProductRequest;
import com.springboot.centralloggingservice.dto.entity.Product;
import com.springboot.centralloggingservice.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    Product save(ProductRequest productRequest);

    List<Product> getProducts();

    Product getProductById(String id) throws ProductNotFoundException;
}
