package com.springboot.centralloggingservice.service;

import com.springboot.centralloggingservice.advice.TrackExecutionTime;
import com.springboot.centralloggingservice.dto.Product;
import com.springboot.centralloggingservice.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private static List<Product> products = new ArrayList<>();

    @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }

    @Override
    @TrackExecutionTime
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Product getProductById(String id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = products.stream().filter(a -> a.getId().equals(id)).findFirst();
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new ProductNotFoundException("product not found");
    }
}
