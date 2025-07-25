package com.flipfit.bean;

import java.time.LocalDate;
import java.time.LocalTime;


/*
 *@author: Kashif, Ihita, Zaid
 *@ClassName: Slot
 *@Exceptions: No
 *@version: 1.0
 *@See :GymCustomer, GymOwner, Registration
 */

public class Slot {
    private int slotId;
    private LocalTime startTime;
    private LocalDate date;
    private int bookedSeats;
    private int centerId;

    public int getSlotId() {
        return this.slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getBookedSeats() {
        return this.bookedSeats;
    }

    public void setBookedSeats(int bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public int getCenterId() {
        return this.centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }
    
}