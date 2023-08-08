package com.springboot.centralloggingservice.service;

import com.springboot.centralloggingservice.dto.Product;
import com.springboot.centralloggingservice.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    List<Product> getProducts();

    Product getProductById(String id) throws ProductNotFoundException;
}
