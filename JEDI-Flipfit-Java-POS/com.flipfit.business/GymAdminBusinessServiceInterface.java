package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymAdmin;

public interface GymAdminBusinessServiceInterface {
	
	// here we need to declare all the services of the customer
	public void createAdmin(GymAdmin gymAdmin);
    public void viewRegisteredGyms();
    public void viewPayments();
    public void cancelbooking(Booking booking);
    public void viewPendingGymCenters();
    public boolean verifyGymCenter(int gymCenter);
    public void addNewRole(String roleName, String roleDesc);
}
