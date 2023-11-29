package com.api.auth.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super("Usuario con email " + email + " ya existe.");
    }
}
