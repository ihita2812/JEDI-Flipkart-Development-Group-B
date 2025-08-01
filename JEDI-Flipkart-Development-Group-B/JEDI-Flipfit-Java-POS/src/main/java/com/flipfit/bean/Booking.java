package com.flipfit.bean;

/*
 * @author: Ihita, Kashif
 * @ClassName: Booking
 * @Exceptions: No
 * @Version: 1.0
 * @See:GymCustomer,
 */
public class Booking {
    private int bookingId;
    private int customerId;
    private int slotId;
    private int status; //0: payment pending, 1: confirmed

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getSlotId() {
        return this.slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }
       
}