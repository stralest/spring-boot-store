package com.codewithmosh.store.product;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId){
        super("Product with id " + productId + " was not found!");
    }
}
