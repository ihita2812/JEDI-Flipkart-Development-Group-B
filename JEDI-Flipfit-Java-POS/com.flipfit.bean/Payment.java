package com.flipfit.bean;
import java.time.LocalDateTime;

/*
 *@author: Eshwar, Ihita, Zaid
 *@ClassName: Payment
 *@Exceptions: No
 *@version: 1.0
 *@See :GymOwner, GymCustomer, Booking
 */

public class Payment {
    private int paymentId;
    private int customerId;
    private float amount;
    private LocalDateTime paymentDateTime;
    private int bookingId;

    public int getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDateTime getPaymentDateTime() {
        return this.paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public int getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    
}
