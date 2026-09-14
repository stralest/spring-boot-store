package com.codewithmosh.store.user.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " was not found!");
    }
}
