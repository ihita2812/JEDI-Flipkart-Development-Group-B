package com.flipfit.bean;

public class GymAdmin extends GymUser {
    private int Admin_id;
    private String Admin_name;

    public int getAdmin_id() {
        return this.Admin_id;
    }

    public void setAdmin_id(int Admin_id) {
        this.Admin_id = Admin_id;
    }

    public String getAdmin_name() {
        return this.Admin_name;
    }

    public void setAdmin_name(String Admin_name) {
        this.Admin_name = Admin_name;
    }
}

