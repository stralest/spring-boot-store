package com.codewithmosh.store.category;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Byte categoryId) {
        super("Category with id " + categoryId + " was not found!");
    }
}
