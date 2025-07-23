package com.flipflit.client;

import java.util.Scanner;

import com.flipfit.business.*;

public class GymCustomerClient {

    public static void customerMenu(int customerId) {

        GymCustomerBusiness customer = new GymCustomerBusiness();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome Customer!");
        System.out.println("Enter your choice");
        System.out.println("\t1\tView gym centers near you");
        System.out.println("\t2\tView your bookings");
        System.out.println("\t5\tLogout");

        int choice = scanner.nextInt();
        scanner.nextLine();  // consume newline

        switch (choice) {
            case 1:
                System.out.println("Here are the centers near you!");
                customer.viewGymCenter(null);
                System.out.println("1\n2\n3\n4\n5");
                System.out.println("You can enter the gym center number to view available slots!");
                System.out.println("Enter 0 to go back!");

                int choice2 = scanner.nextInt();
                scanner.nextLine();

                if (choice2 == 0) {
                    break;
                } else {
                    System.out.println("Here are the available slots!");
                    customer.viewSlot(choice2);
                    System.out.println("1\n2\n3\n4\n5");
                    System.out.println("You can enter the slot!");
                    System.out.println("Enter 0 to go back!");

                    int choice3 = scanner.nextInt();
                    scanner.nextLine();

                    if (choice3 == 0) {
                        break;
                    } else {
                        customer.bookSlot(choice3);
                    }
                }
        }

        scanner.close();


    }
}
