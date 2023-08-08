package com.springboot.centralloggingservice.exception.handler;

import com.springboot.centralloggingservice.exception.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Custom exception to handle any type of exception related to microservices calls
     */
    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<String> handleServiceConnectionException(ProductNotFoundException e) {
        logger.error("Service connection exception " + e);
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

    /**
     * Handles generic exception that is not caught by any of the upper exceptions defined
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Exception " + e);
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }

}
