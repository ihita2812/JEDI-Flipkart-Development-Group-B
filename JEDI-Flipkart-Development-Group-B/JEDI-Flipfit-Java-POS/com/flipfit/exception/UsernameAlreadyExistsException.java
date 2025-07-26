package com.flipfit.exception;

/**
 * This exception is thrown when a user tries to register with a username
 * that already exists in the system.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username '" + username + "' is already taken. Please choose another.");
    }
}