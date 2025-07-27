package com.flipfit.exception;

/**
 * This exception is thrown during a login attempt if the username is not found,
 * the password does not match, or the selected role is incorrect.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid username, password, or role provided. Please try again.");
    }
}