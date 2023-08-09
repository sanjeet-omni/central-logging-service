package com.springboot.centralloggingservice.repository;

import com.springboot.centralloggingservice.advice.TrackExecutionTime;
import com.springboot.centralloggingservice.dto.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @TrackExecutionTime
    @Query(value = "SELECT p from Product p where id = :id")
    Optional<Product> fetchById(String id);
}
