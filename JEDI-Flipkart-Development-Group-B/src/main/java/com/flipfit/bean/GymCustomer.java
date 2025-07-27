package com.flipfit.bean;

/*
 * @author: Ritesh, Zaid
 * @ClassName: GymCustomer
 * @Exceptions: No
 * @Version: 1.0
 * @See:GymUser, Payment, Registration, Slot
 */
public class GymCustomer extends GymUser{
    private int customerId;
    private int age;
    private String location;
    private int gender;
    private String email;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }    

}
