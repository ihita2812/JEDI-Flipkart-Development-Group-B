package com.flipfit.client;

import java.util.Scanner;
import com.flipfit.business.*;

public class GymCustomerMenu {

    public static void customerMenu(int customerId) {

        GymCustomerBusinessServiceInterface customer = new GymCustomerBusinessService();


        Scanner scanner = new Scanner(System.in);
        boolean more = true;

        while (more) {
            System.out.println("---------------------------------------------");
            System.out.println("Welcome Customer!");
            System.out.println("Enter your choice");
            System.out.println("\t1\tView gym centers near you");
            System.out.println("\t2\tView your bookings");
            System.out.println("\t3\tLogout");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
            
            case 1:
                System.out.println("---------------------------------------------");
                System.out.println("Here are the centers near you!");
                customer.viewGymCenter(null);
                for (int i = 1; i < 5; i++) {
                    System.out.println("GYM CENTER " + i);
                }
                System.out.println("---------------------------------------------");
                System.out.println("You can enter the gym center number to view available slots!");
                System.out.println("Enter 0 to go to home page!");

                int choice2 = scanner.nextInt();
                scanner.nextLine();

                if (choice2 == 0) {
                    break;
                } else {
                    System.out.println("---------------------------------------------");
                    System.out.println("Here are the available slots!");
                    customer.viewSlot(choice2);
                    for (int i = 1; i < 5; i++) {
                        System.out.println("SLOT NUMBER " + i);
                    }
                    System.out.println("---------------------------------------------");
                    System.out.println("You can enter the slot number to book it!");
                    System.out.println("Enter 0 to go to home page!");

                    int choice3 = scanner.nextInt();
                    scanner.nextLine();

                    if (choice3 == 0) {
                        break;
                    } else {
                        System.out.println("---------------------------------------------");
                        customer.bookSlot(choice3);
                        int bookingId = 0;
                        System.out.println("Make payment atleast 6 hours before the slot timing to confirm!");
                        System.out.println("Enter 1 to pay now, 0 to pay later and go to home page!");

                        int choice4 = scanner.nextInt();
                        scanner.nextLine();

                        if (choice4 == 1) {
                            System.out.println("---------------------------------------------");
                            customer.makePayment(bookingId);
                        } else {
                            more = false;
                        }

                    }
                }
                break;
            
            case 2:
                System.out.println("---------------------------------------------");
                System.out.println("Here are your bookings!");
                customer.viewBookings();
                for (int i = 1; i < 5; i++) {
                    System.out.println("BOOKING NUMBER " + i);
                }
                System.out.println("---------------------------------------------");
                System.out.println("You can enter the booking number number to cancel it!");
                System.out.println("Enter 0 to go back!");

                int choice5 = scanner.nextInt();
                scanner.nextLine();

                if (choice5 == 0) {
                    break;
                } else {
                    System.out.println("---------------------------------------------");
                    customer.cancelbooking(choice5);
                }
                break;

            case 3:
                more = false;
                break;
            
            default:
                System.out.println("---------------------------------------------");
                System.out.println("Bad choice :/");
                
            
            }
        }
    
        scanner.close();

    }

}
