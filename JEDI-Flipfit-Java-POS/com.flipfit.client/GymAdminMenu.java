package com.flipfit.client;

import com.flipfit.business.*;
import java.util.*;
import com.flipfit.bean.*;
public class GymAdminMenu {
        public static void adminMenu(int adminId) {
        
        GymAdminBusinessServiceInterface admin = new GymAdminBusinessService();
        GymUserBusinessServiceInterface user = new GymUserBusinessService();
        Scanner scanner = new Scanner(System.in);
        boolean valid = true;    

         while(valid)
        {
        
        System.out.println("---------------------------------------------");
        System.out.println("Welcome Admin!");
        System.out.println("Enter your choice");
        System.out.println("\t1\tView all registered gym centers");
        System.out.println("\t2\tView pending gym center approvals");
        System.out.println("\t3\tView all payments");
//        System.out.println("\t4\tAdd Roles");
        System.out.println("\t4\tAdd Gym Customer");
        System.out.println("\t5\tRemove Gym Customer");
        System.out.println("\t6\tAdd Gym Owner");
        System.out.println("\t7\tRemove Gym Owner");
        System.out.println("\t8\tLogout");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice)
        {
            case 1:
                List<GymCenter> gymCenters= admin.viewRegisteredGyms();
                if (gymCenters.isEmpty()) {
                    System.out.println("No registered gym centers found.");
                } else {
                    System.out.println("---------------------------------------------");
                    System.out.println("Here are the registered gym centers!");
                    for (GymCenter gymCenter : gymCenters) {
                        System.out.println("---------------------------------------------");
                        System.out.println("Gym ID: " + gymCenter.getCenterId());
                        System.out.println("Gym Name: " + gymCenter.getName());
                        System.out.println("Owner ID: " + gymCenter.getOwnerId());
                        System.out.println("Location: " + gymCenter.getLocation());
                        System.out.println("Capacity: "+  gymCenter.getCapacity());
                        System.out.println("Number of Slots: " + gymCenter.getNumSlots());
                        if(gymCenter.getApprovalStatus() == 0) {
                            System.out.println("Approval Status: Rejected");
                        } else if(gymCenter.getApprovalStatus() == 1) {
                            System.out.println("Approval Status: Approved");
                        } else {
                            System.out.println("Approval Status: Pending");
                        }
                    }
                }
                System.out.println("---------------------------------------------");
                break;
            case 2:
                List<GymCenter> pendingGyms = admin.viewPendingGymCenters();
                if (pendingGyms.isEmpty()) {
                    System.out.println("No Pending gym centers found.");
                }
                else{
                    for(GymCenter gymCenter : pendingGyms) {
                        System.out.println("---------------------------------------------");
                        System.out.println("Gym ID: " + gymCenter.getCenterId());
                        System.out.println("Gym Name: " + gymCenter.getName());
                        System.out.println("Owner ID: " + gymCenter.getOwnerId());
                        System.out.println("Location: " + gymCenter.getLocation());
                        System.out.println("Capacity: "+  gymCenter.getCapacity());
                        System.out.println("Number of Slots: " + gymCenter.getNumSlots());
                    }
                }

                System.out.println("Enter Gym Center ID to approve or reject or Enter 0 to go back");
                int gymCenterId = scanner.nextInt();
                if(gymCenterId == 0)
                    break;
                System.out.println("Enter 1 to approve or 0 to reject");
                int approvalStatus = scanner.nextInt();
                int verificationStatus = admin.verifyGymCenter(gymCenterId,approvalStatus);
                if (verificationStatus == 1) {
                    System.out.println("Approval Status: Approved");
                }
                else if (verificationStatus == 0) {
                    System.out.println("Approval Status: Rejected");
                } else if (verificationStatus == -1) {
                    System.out.println("Invalid status. Please enter 1 for approve or 0 for reject. Exiting to menu");
                } else if (verificationStatus == -2) {
                    System.out.println("Gym Center is already either approved or rejected.");
                }
                break;
            case 3:
                List<Payment> payments = admin.viewPayments();
                if (payments.isEmpty()) {
                    System.out.println("No payments found.");
                } else {
                    System.out.println("---------------------------------------------");
                    System.out.println("Here are the payments!");
                    for (Payment payment : payments) {
                        System.out.println("---------------------------------------------");
                        System.out.println("Payment ID: " + payment.getPaymentId());
                        System.out.println("Customer ID: " + payment.getCustomerId());
                        System.out.println("Amount: " + payment.getAmount());
                        System.out.println("Date: " + payment.getPaymentDateTime());
                    }
                }
                break;
            case 4:
                System.out.println("Enter Gym Customer Username to be added");
                String customerUsername = scanner.nextLine();
                scanner.nextLine();

                if(user.userNameExists(customerUsername)) {
                    System.out.println("Username already exists. Please choose a different username.");
                    break;
                }
                System.out.println("Enter Gym Customer Name to be added");
                String customerName = scanner.nextLine();
                scanner.nextLine();
                System.out.println("Enter Gym Customer Password to be added");
                String password = scanner.nextLine();
                scanner.nextLine();
                System.out.println("Enter Gym Customer Age to be added");
                int age = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter Gym Customer Location to be added");
                String location = scanner.nextLine();
                scanner.nextLine();
                System.out.println("Enter Gym Customer Gender to be added");
                int gender = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter Gym Customer Email to be added");
                String customerEmail = scanner.nextLine();
                scanner.nextLine();

                System.out.println("Enter Gym Customer Phone to be added");
                String customerPhone = scanner.nextLine();
                scanner.nextLine();
                admin.addGymCustomer(customerUsername,  customerName,  password,  age,  location,  gender,  customerEmail,  customerPhone);
                System.out.println("Gym Customer " + customerName + " added.");
                System.out.println("---------------------------------------------");
                break;
            case 5:
                System.out.println("Enter Gym Customer ID to be removed");
                int customerId = scanner.nextInt();
                scanner.nextLine();
                if(admin.removeGymCustomer(customerId)) // Assuming this method exists
                System.out.println("Gym Customer with ID " + customerId + " removed.");
                else System.out.println("Gym Customer with ID " + customerId + " not found.");
                break;
            case 6:
                System.out.println("Enter Gym Owner Username to be added");
                String userName = scanner.nextLine();
                if(user.userNameExists(userName)) {
                    System.out.println("Username already exists. Please choose a different username.");
                    break;
                }
                scanner.nextLine();

                System.out.println("Enter Gym Owner Name to be added");
                String ownerName = scanner.nextLine();
                scanner.nextLine();

                System.out.println("Enter Gym Owner Password to be added");
                String Ownerpassword = scanner.nextLine();
                scanner.nextLine();

                System.out.println("Enter Gym Owner Gender to be added");
                int ownerGender = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter Gym Owner Email to be added");
                String ownerEmail = scanner.nextLine();
                scanner.nextLine();

                System.out.println("Enter Gym Owner Phone to be added");
                String ownerPhone = scanner.nextLine();
                scanner.nextLine();
                admin.addGymOwner(userName,ownerName, Ownerpassword,ownerGender, ownerEmail,ownerPhone); // Assuming this method exists
                System.out.println("Gym Owner " + ownerName + " added.");
                break;
            case 7:
                System.out.println("Enter Gym Owner ID to be removed");
                int ownerId = scanner.nextInt();
                scanner.nextLine();
                if(admin.removeGymOwner(ownerId)) // Assuming this method exists
                System.out.println("Gym Owner with ID " + ownerId + " removed.");
                else System.out.println("Gym Owner with ID " + ownerId + " not found.");
                break;
            case 8:
                System.out.println("See you again!");
                return;
            default:
                System.out.println("Invalid choice.");
        }
        }
        scanner.close();
    }
}