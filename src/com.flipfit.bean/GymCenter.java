package com.flipfit.bean;

public class GymCenter{
    private int centerId;
    private String name;
    private String location;
    private int capacity;
    private int numSlots;

    public int getNumSlots() {
        return this.numSlots;
    }

    public void setNumSlots(int numSlots) {
        this.numSlots = numSlots;
    }

    public int getCenterId() {
        return this.centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
}