package com.flipfit.exception;

/**
 * This exception is thrown when a customer attempts to make a payment
 * for a slot that has no remaining capacity.
 */
public class SlotFullException extends RuntimeException {
    public SlotFullException(int slotId) {
        super("Payment failed because the slot with ID " + slotId + " is now full.");
    }
}