package com.flipfit.bean;

public Class Booking {
    private int bookingId;
    private GymCustomer.customerId customerId;
    private Slot.slotId slotId;


    public Class getBooking() {
        return Booking;
    }

    public void setBooking(Class booking) {
        Booking = booking;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public GymCustomer.customerId getCustomerId() {
        return customerId;
    }

    public void setCustomerId(GymCustomer.customerId customerId) {
        this.customerId = customerId;
    }

    public Slot.slotId getSlotId() {
        return slotId;
    }

    public void setSlotId(Slot.slotId slotId) {
        this.slotId = slotId;
    }
}