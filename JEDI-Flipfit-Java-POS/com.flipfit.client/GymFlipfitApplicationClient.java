package com.flipfit.client;

import java.util.Scanner;

import com.flipfit.bean.*;
import com.flipfit.business.*;
import com.flipfit.client.GymCustomerMenu;
import com.flipfit.client.GymAdminMenu;
import com.flipfit.client.GymOwnerMenu;

public class GymFlipfitApplicationClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GymCustomerBusiness customerBusiness = new GymCustomerBusiness();
        GymOwnerBusiness ownerBusiness = new GymOwnerBusiness();
        GymUserBusiness userBusiness = new GymUserBusiness();
        boolean more = true;

        while (more) {
            System.out.println("---------------------------------------------");
            System.out.println("Welcome to the Flipfit application");
            System.out.println("Enter your choice");
            System.out.println("\t1\tLogin");
            System.out.println("\t2\tReg of Gym Customer");
            System.out.println("\t3\tReg of Gym Owner");
            System.out.println("\t4\tSave Password");
            System.out.println("\t5\tExit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Role (0: Customer, 1: Owner, 2: Admin): ");
                    int role = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    
                    if (username.equals("user") && password.equals("pass")) {
                        switch (role) {
                            case 0:
                                GymCustomerMenu.customerMenu(0);
                                break;
                            case 1:
                                GymOwnerMenu.ownerMenu();
                                break;
                            case 2:
                                GymAdminMenu.adminMenu(0);
                                break;
                        }
                    } else {
                        System.out.println("Invalid credentials. Try again.");
                    }
                    break;

                case 2:
                    GymCustomer customer = new GymCustomer();
                    customerBusiness.registerCustomer(customer);
                    break;

                case 3:
                    GymOwner owner = new GymOwner();
                    ownerBusiness.registerOwner(owner);
                    break;

                case 4:
                    System.out.println("Password Saved. (dummy)");
                    break;

                case 5:
                    System.out.println("Thank you for using Flipfit. Exiting...");
                    scanner.close();
                    more = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please select again.");
                    
            }
        }

        scanner.close();
    }

}