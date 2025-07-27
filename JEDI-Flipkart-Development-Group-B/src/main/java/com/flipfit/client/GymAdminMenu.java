package com.flipfit.client;

import com.flipfit.bean.GymCenter;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Payment;
import com.flipfit.business.GymAdminBusinessService;
import com.flipfit.business.GymAdminBusinessServiceInterface;
import com.flipfit.business.GymUserBusinessService;
import com.flipfit.business.GymUserBusinessServiceInterface;
import com.flipfit.exception.GymNotFoundException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
/*
 *@author:Ritesh, Ritesh, Kashif
 *@ClassName:GymAdminMenu
 *@version:1.0
 *@See :GymAdminBusinessServiceInterface, GymUserBusinessServiceInterface
 */
public class GymAdminMenu {
        public static void adminMenu(int adminId) {
        
        GymAdminBusinessServiceInterface admin = new GymAdminBusinessService();
        GymUserBusinessServiceInterface user = new GymUserBusinessService();
        Scanner scanner = new Scanner(System.in);
        boolean valid = true;    

         while(valid) {


             System.out.printf("%n*********************************************%n");
             System.out.printf("         ADMINISTRATOR DASHBOARD%n");
             System.out.printf("*********************************************%n");

             System.out.println("Welcome Admin!");

             System.out.println("\n--- Gym Management ---");
             System.out.println("  1. View all registered gym centers");
             System.out.println("  2. Manage Pending Gym Approvals");
             System.out.println("  3. View All Platform Payments");

             System.out.println("\n--- User Management ---");
             System.out.println("  4. View All Gym Owners");
             System.out.println("  5. View All Customers");
             System.out.println("  6. Add a New Customer");
             System.out.println("  7. Remove a Customer");
             System.out.println("  8. Add a New Gym Owner");
             System.out.println("  9. Remove a Gym Owner");

             System.out.println("  0. Logout");

             System.out.print("\nEnter your choice -> ");
             try{
                 int choice = scanner.nextInt();
                 scanner.nextLine(); // consume newline

                 switch (choice) {
                     case 1:

                         List<GymCenter> gymCenters = admin.viewRegisteredGyms();

                         System.out.printf("%n****************** ALL REGISTERED GYM CENTERS ******************%n");

                         if (gymCenters.isEmpty()) {
                             System.out.println("\n-- No gym centers have been registered on the platform yet. --\n");
                         } else {
                             System.out.printf("--------------------------------------------------------------------------------------------------%n");
                             System.out.printf("| %-8s | %-25s | %-15s | %-8s | %-8s | %-15s |%n", "GYM ID", "NAME", "LOCATION", "OWNER ID", "CAPACITY", "STATUS");
                             System.out.printf("--------------------------------------------------------------------------------------------------%n");

                             for (GymCenter gymCenter : gymCenters) {
                                 String status = switch (gymCenter.getApprovalStatus()) {
                                     case 0 -> "Rejected";
                                     case 1 -> "Approved";
                                     default -> "Pending";
                                 };

                                 System.out.printf("| %-8d | %-25s | %-15s | %-8d | %-8d | %-15s |%n",
                                         gymCenter.getCenterId(),
                                         gymCenter.getName(),
                                         gymCenter.getLocation(),
                                         gymCenter.getOwnerId(),
                                         gymCenter.getCapacity(),
                                         status);
                             }
                             System.out.printf("--------------------------------------------------------------------------------------------------%n");
                         }
                         break;
                     case 2:
                         List<GymCenter> pendingGyms = admin.viewPendingGymCenters();

                         System.out.printf("%n****************** PENDING GYM APPROVALS ******************%n");

                         if (pendingGyms.isEmpty()) {
                             System.out.println("\n-- There are no gym centers awaiting approval at this time. --\n");
                         } else {
                             System.out.printf("--------------------------------------------------------------------------------%n");
                             System.out.printf("| %-8s | %-25s | %-15s | %-8s | %-8s |%n", "GYM ID", "NAME", "LOCATION", "OWNER ID", "CAPACITY");
                             System.out.printf("--------------------------------------------------------------------------------%n");

                             for (GymCenter gymCenter : pendingGyms) {
                                 System.out.printf("| %-8d | %-25s | %-15s | %-8d | %-8d |%n",
                                         gymCenter.getCenterId(),
                                         gymCenter.getName(),
                                         gymCenter.getLocation(),
                                         gymCenter.getOwnerId(),
                                         gymCenter.getCapacity());
                             }
                             System.out.printf("--------------------------------------------------------------------------------%n");

                             System.out.print("\n> Enter the GYM ID to process an approval (or 0 to go back): ");
                             int gymCenterId = scanner.nextInt();
                             scanner.nextLine(); // consume newline

                             if (gymCenterId != 0) {
                                 System.out.print("> Enter 1 to APPROVE or 0 to REJECT: ");
                                 int approvalStatus = scanner.nextInt();
                                 scanner.nextLine(); // consume newline

                                 try {
                                     int verificationStatus = admin.verifyGymCenter(gymCenterId, approvalStatus);
                                     // The switch now only needs to handle success cases and the -1 for invalid input
                                     switch (verificationStatus) {
                                         case 1:
                                             System.out.printf("\n[SUCCESS] Gym Center %d has been APPROVED.\n", gymCenterId);
                                             break;
                                         case 0:
                                             System.out.printf("\n[SUCCESS] Gym Center %d has been REJECTED.\n", gymCenterId);
                                             break;
                                         case -1:
                                             System.out.println("\n[ERROR] Invalid action. Please enter 1 for approve or 0 for reject.\n");
                                             break;
                                         default:
                                             System.out.println("\n[ERROR] An unknown error occurred.\n");
                                             break;
                                     }
                                 } catch (GymNotFoundException e) {
                                     System.out.println("\n[ERROR] " + e.getMessage() + "\n");
                                 }
                             }
                         }
                         break;
                     //            case 3:
                     //                List<Payment> payments = admin.viewPayments();
                     //                if (payments.isEmpty()) {
                     //                    System.out.println("No payments found.");
                     //                } else {
                     //                    System.out.println("---------------------------------------------");
                     //                    System.out.println("Here are the payments!");
                     //                    for (Payment payment : payments) {
                     //                        System.out.println("---------------------------------------------");
                     //                        System.out.println("Payment ID: " + payment.getPaymentId());
                     //                        System.out.println("Customer ID: " + payment.getCustomerId());
                     //                        System.out.println("Amount: " + payment.getAmount());
                     //                        System.out.println("Date: " + payment.getPaymentDateTime());
                     //                    }
                     //                }
                     //                break;
                     case 3:
                         List<Payment> payments = admin.viewPayments();

                         System.out.printf("%n********************* ALL PLATFORM PAYMENTS *********************%n");

                         if (payments.isEmpty()) {
                             System.out.println("\n-- No payments have been recorded on the platform yet. --\n");
                         } else {
                             System.out.printf("-----------------------------------------------------------------------%n");
                             System.out.printf("| %-12s | %-12s | %-12s | %-25s |%n", "PAYMENT ID", "CUSTOMER ID", "AMOUNT", "DATE & TIME");
                             System.out.printf("-----------------------------------------------------------------------%n");

                             for (Payment payment : payments) {
                                 System.out.printf("| %-12d | %-12d | %-12.2f | %-25s |%n",
                                         payment.getPaymentId(),
                                         payment.getCustomerId(),
                                         payment.getAmount(),
                                         payment.getPaymentDateTime().toString());
                             }
                             System.out.printf("-----------------------------------------------------------------------%n");
                         }
                         break;
                     //            case 4:
                     //                List<GymOwner> allOwners= admin.getAllOwners();
                     //                for(GymOwner owner : allOwners) {
                     //                    System.out.println("---------------------------------------------");
                     //                    System.out.println("Owner ID: " + owner.getOwnerId());
                     //                    System.out.println("Owner Name: " + owner.getName());
                     //                }
                     //                break;
                     case 4:
                         List<GymOwner> allOwners = admin.getAllOwners();

                         System.out.printf("%n**************************************** ALL GYM OWNERS ****************************************%n");

                         if (allOwners.isEmpty()) {
                             System.out.println("\n-- There are no gym owners registered on the platform. --\n");
                         } else {
                             System.out.printf("------------------------------------------------------------------------------------------------%n");
                             System.out.printf("| %-12s | %-20s | %-25s | %-30s |%n", "OWNER ID", "USERNAME", "NAME", "EMAIL");
                             System.out.printf("------------------------------------------------------------------------------------------------%n");

                             for (GymOwner owner : allOwners) {
                                 System.out.printf("| %-12d | %-20s | %-25s | %-30s |%n",
                                         owner.getOwnerId(),
                                         owner.getUserName(),
                                         owner.getName(),
                                         owner.getEmail());
                             }
                             System.out.printf("------------------------------------------------------------------------------------------------%n");
                         }
                         break;
                     //            case 5:
                     //                List<GymCustomer> allCustomer= admin.getAllCustomer();
                     //                for(GymCustomer customer : allCustomer){
                     //                    System.out.println("---------------------------------------------");
                     //                    System.out.println("Customer ID: " + customer.getCustomerId());
                     //                    System.out.println("Customer Name: " + customer.getName());
                     //                }
                     //                break;
                     case 5:
                         List<GymCustomer> allCustomers = admin.getAllCustomer();

                         System.out.printf("%n************************************** ALL CUSTOMERS **************************************%n");

                         if (allCustomers.isEmpty()) {
                             System.out.println("\n-- There are no customers registered on the platform. --\n");
                         } else {
                             System.out.printf("----------------------------------------------------------------------------------------%n");
                             System.out.printf("| %-12s | %-20s | %-25s | %-15s |%n", "CUSTOMER ID", "USERNAME", "NAME", "LOCATION");
                             System.out.printf("----------------------------------------------------------------------------------------%n");

                             for (GymCustomer customer : allCustomers) {
                                 System.out.printf("| %-12d | %-20s | %-25s | %-15s |%n",
                                         customer.getCustomerId(),
                                         customer.getUserName(),
                                         customer.getName(),
                                         customer.getLocation());
                             }
                             System.out.printf("----------------------------------------------------------------------------------------%n");
                         }
                         break;
                     case 6:
                         System.out.printf("%n********************* ADD NEW GYM CUSTOMER *********************%n");

                         System.out.print("> Enter Username: ");
                         String customerUsername = scanner.nextLine();

                         if (user.userNameExists(customerUsername)) {
                             System.out.println("\n[ERROR] Username '" + customerUsername + "' already exists. Please choose a different username.\n");
                             break;
                         }

                         System.out.print("> Enter Full Name: ");
                         String customerName = scanner.nextLine();

                         System.out.print("> Enter Password: ");
                         String password = scanner.nextLine();

                         System.out.print("> Enter Age: ");
                         int age = scanner.nextInt();
                         scanner.nextLine(); // Consume newline

                         System.out.print("> Enter Location: ");
                         String location = scanner.nextLine();

                         System.out.print("> Enter Gender (0 for Male, 1 for Female): ");
                         int gender = scanner.nextInt();
                         scanner.nextLine(); // Consume newline

                         System.out.print("> Enter Email Address: ");
                         String customerEmail = scanner.nextLine();

                         System.out.print("> Enter Phone Number: ");
                         String customerPhone = scanner.nextLine();

                         admin.addGymCustomer(customerUsername, customerName, password, age, location, gender, customerEmail, customerPhone);

                         System.out.printf("\n[SUCCESS] Gym Customer '%s' has been added successfully.\n", customerName);
                         break;
                     case 7:
                         System.out.printf("%n********************* REMOVE GYM CUSTOMER *********************%n");
                         System.out.print("> Enter the Customer ID of the user you wish to remove: ");
                         int customerId = scanner.nextInt();
                         scanner.nextLine(); // Consume newline

                         // Optional: Add a confirmation step to prevent accidental deletion
                         System.out.print("> Are you sure you want to remove customer " + customerId + "? (Y/N): ");
                         String confirmation = scanner.nextLine();

                         if (confirmation.equalsIgnoreCase("Y")) {
                             if (admin.removeGymCustomer(customerId)) {
                                 System.out.printf("\n[SUCCESS] Gym Customer with ID %d has been removed.\n", customerId);
                             } else {
                                 System.out.printf("\n[ERROR] Gym Customer with ID %d could not be found.\n", customerId);
                             }
                         } else {
                             System.out.println("\n[INFO] Action cancelled. The customer was not removed.\n");
                         }
                         break;
                     case 8:
                         System.out.printf("%n********************* ADD NEW GYM OWNER *********************%n");

                         System.out.print("> Enter Username: ");
                         String userName = scanner.nextLine();

                         if (user.userNameExists(userName)) {
                             System.out.println("\n[ERROR] Username '" + userName + "' already exists. Please choose a different username.\n");
                             break;
                         }

                         System.out.print("> Enter Full Name: ");
                         String ownerName = scanner.nextLine();

                         System.out.print("> Enter Password: ");
                         String ownerPassword = scanner.nextLine();

                         System.out.print("> Enter Gender (0 for Male, 1 for Female): ");
                         int ownerGender = scanner.nextInt();
                         scanner.nextLine(); // Consume newline

                         System.out.print("> Enter Email Address: ");
                         String ownerEmail = scanner.nextLine();

                         System.out.print("> Enter Phone Number: ");
                         String ownerPhone = scanner.nextLine();

                         admin.addGymOwner(userName, ownerName, ownerPassword, ownerGender, ownerEmail, ownerPhone);

                         System.out.printf("\n[SUCCESS] Gym Owner '%s' has been added successfully.\n", ownerName);
                         break;
                     case 9:
                         System.out.printf("%n********************* REMOVE GYM OWNER *********************%n");
                         System.out.print("> Enter the Owner ID of the user you wish to remove: ");
                         int ownerId = scanner.nextInt();
                         scanner.nextLine(); // Consume newline

                         // Add a confirmation step to prevent accidental deletion
                         System.out.print("> Are you sure you want to remove owner " + ownerId + "? This action cannot be undone. (Y/N): ");
                         String ownerConfirmation = scanner.nextLine();

                         if (ownerConfirmation.equalsIgnoreCase("Y")) {
                             if (admin.removeGymOwner(ownerId)) {
                                 System.out.printf("\n[SUCCESS] Gym Owner with ID %d has been removed.\n", ownerId);
                             } else {
                                 System.out.printf("\n[ERROR] Gym Owner with ID %d could not be found.\n", ownerId);
                             }
                         } else {
                             System.out.println("\n[INFO] Action cancelled. The owner was not removed.\n");
                         }
                         break;
                     case 0:
                         System.out.println("Logging out , See you again!!!");
                         return;
                     default:
                         System.out.println("Invalid choice.");

                 }
             }catch (InputMismatchException e) {
                 System.out.println("\n[ERROR] Invalid input. Please enter a number.\n");
                 scanner.nextLine(); // Clear the bad input
             }
         }
        scanner.close();
    }
}