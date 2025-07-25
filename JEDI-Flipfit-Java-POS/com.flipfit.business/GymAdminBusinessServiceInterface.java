package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymAdmin;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Payment;

import java.util.*;
public interface GymAdminBusinessServiceInterface {
	
	// here we need to declare all the services of the customer
	public void createAdmin(GymAdmin gymAdmin);
    public List<GymCenter> viewRegisteredGyms();
    public List<Payment> viewPayments();
    public void cancelbooking(Booking booking);
    public List<GymCenter>  viewPendingGymCenters();
    public int verifyGymCenter(int gymCenter, int approvalStatus);
    public void addNewRole(String roleName, String roleDesc);
    public void addGymCustomer(String userName, String customerName, String password, int age, String location, int gender, String customerEmail, String customerPhone);
    public boolean removeGymCustomer(int customerId);
    public void addGymOwner(String userName,String ownerName, String password, int gender, String ownerEmail, String ownerPhone);
    public boolean removeGymOwner(int ownerId);
}
