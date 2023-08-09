package com.springboot.centralloggingservice.service;

import com.springboot.centralloggingservice.advice.TrackExecutionTime;
import com.springboot.centralloggingservice.dto.ProductRequest;
import com.springboot.centralloggingservice.dto.entity.Product;
import com.springboot.centralloggingservice.exception.ProductNotFoundException;
import com.springboot.centralloggingservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product save(ProductRequest productRequest) {
        Product product = new Product(productRequest.getId(), productRequest.getName(), productRequest.getPrice());
        product = productRepository.save(product);
        return product;
    }

    @Override
    @TrackExecutionTime
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.fetchById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new ProductNotFoundException("product not found");
    }
}
