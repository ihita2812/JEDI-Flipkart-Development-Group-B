package com.flipfit.exception;

/**
 * This exception is thrown when an admin attempts to verify a gym center
 * that either does not exist or is not in a pending state.
 */
public class GymNotFoundException extends RuntimeException {
    public GymNotFoundException(int gymId) {
        super("Gym Center with ID " + gymId + " was not found or is not in a pending state.");
    }
}