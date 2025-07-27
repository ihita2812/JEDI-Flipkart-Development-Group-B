package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.*;
import com.flipfit.exception.GymNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
 *@author: Zaid, Ihita, Eshwar
 *@ClassName: GymAdminBusinessService
 *@version: 1.0
 *@See :GymAdminBusinessServiceInterface, GymOwnerBusinessService, GymUserBusinessService, GymCustomerBusinessService
 */
public class GymAdminBusinessService implements GymAdminBusinessServiceInterface
{
    GymUserDAO gymUserDAO = new GymUserDAOImpl();
    GymCustomerDAO gymCustomerDAO = new GymCustomerDAOImpl();
    GymOwnerDAO gymOwnerDAO = new GymOwnerDAOImpl();
    GymUserBusinessService userBusiness = new GymUserBusinessService();
    GymOwnerBusinessService ownerBusiness = new GymOwnerBusinessService();
    GymCustomerBusinessService customerBusiness = new GymCustomerBusinessService();
    public void createAdmin(GymAdmin gymAdmin){
        System.out.println("Creating Admin");
    }

    public List<GymCenter> viewRegisteredGyms()
    {
        List<GymCenter> gymCenters = gymUserDAO.getAllCenters();
        if (gymCenters.isEmpty()) {
            return Collections.emptyList(); // Return empty list if no gyms are registered
        }
       return gymCenters;
    }

    public List<Payment> viewPayments()
    {
        return gymUserDAO.getAllPayments();
    }

    public void cancelbooking(Booking booking){
        System.out.println("Booking Cancelled");
    }

    public List<GymCenter> viewPendingGymCenters() {
        List<GymCenter> gymCenters = gymUserDAO.getAllCenters();
        List<GymCenter> viewPendingGymCenters = new ArrayList<GymCenter>();
        for(GymCenter gymCenter : gymCenters)
        {
            if(gymCenter.getApprovalStatus() == 2) { // Assuming 2 means pending
                viewPendingGymCenters.add(gymCenter);
            }
        }
        return viewPendingGymCenters;
    }

    public int verifyGymCenter(int gymCenterId, int approvalStatus) {

        if (approvalStatus != 0 && approvalStatus != 1) {
            return -1;
        }

        // 2. Fetch the gym center details to get the owner's ID for notification
        GymCenter centerToVerify = gymUserDAO.getCenterById(gymCenterId);

        // Check if the gym center exists and is actually pending
        if (centerToVerify == null || centerToVerify.getApprovalStatus() != 2) {
            throw new GymNotFoundException(gymCenterId);
        }

        boolean updateSuccess = gymUserDAO.updateGymCenterApprovalStatus(gymCenterId, approvalStatus);

        if (updateSuccess) {
            // If the update was successful, create and send a notification
            String message;
            if (approvalStatus == 1) {
                message = "Congratulations! Your Gym Center '" + centerToVerify.getName() + "' (ID: " + gymCenterId + ") has been approved.";
            } else {
                message = "We regret to inform you that your Gym Center '" + centerToVerify.getName() + "' (ID: " + gymCenterId + ") has been rejected.";
            }

            Notification ownerNotification = new Notification();
            ownerNotification.setMessage(message);

            // Get the userId of the owner to send the notification
            int ownerId = centerToVerify.getOwnerId();
            int userId = gymUserDAO.getUserIdFromOwnerId(ownerId);
            ownerNotification.setUserId(userId);

            gymUserDAO.addNotification(ownerNotification);

            // Return the status that was successfully set
            return approvalStatus;
        }

        // This would happen if the DB update failed for some reason
        return -3;
    }

    public void addNewRole(String roleName, String roleDesc) {
        System.out.println("[New role added to db!]");
    }
    
    public void addGymCustomer(String userName, String customerName, String password, int age, String location, int gender, String customerEmail, String customerPhone) {
        GymUser newUser = userBusiness.createUserBean(customerName, password, 0, userName);
        userBusiness.addUser(newUser);
        GymCustomer newCustomer = customerBusiness.createCustomerBean(customerName, password, 0, userName, age, location, gender, customerEmail, newUser.getUserId());
        customerBusiness.registerCustomer(newCustomer);
        return ;
    }
    
    public boolean removeGymCustomer(int customerId) {
        return gymCustomerDAO.removeCustomer(customerId);

    }
    
    public void addGymOwner(String userName,String ownerName, String password, int gender, String ownerEmail, String ownerPhone) {
        GymUser newUser = userBusiness.createUserBean(ownerName, password, 1, userName);
        userBusiness.addUser(newUser);
        GymOwner newOwner = ownerBusiness.createOwnerBean(ownerName, password, 1, userName, gender, ownerEmail, newUser.getUserId());
        ownerBusiness.registerOwner(newOwner);
        return ;
    }

    public boolean removeGymOwner(int ownerId) {
        return gymOwnerDAO.removeOwner(ownerId);

    }
    public List<GymOwner> getAllOwners() {
        // Step 1: Get the list of all owners.
        List<GymOwner> allOwners = gymUserDAO.getAllOwners();

        // Step 2: Loop through each owner and fetch their specific gyms.
        for (GymOwner owner : allOwners) {
            // For each owner, call the DAO method to get their gyms.
            List<GymCenter> gymsForOwner = gymUserDAO.getAllCentersByOwnerId(owner.getOwnerId());
            owner.setGyms(gymsForOwner);
        }

        return allOwners;
    }
    public List<GymCustomer> getAllCustomer(){
        return gymUserDAO.getAllCustomers();
    }

}