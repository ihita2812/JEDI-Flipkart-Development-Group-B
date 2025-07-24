package com.flipfit.client;

import java.util.*;
import com.flipfit.business.*;
import com.flipfit.bean.*;
import java.time.LocalTime;

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
            System.out.println("\t3\tEdit Profile");
            System.out.println("\t4\tLogout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

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
                        // customer.viewSlot(choice2);
                        // for (int i = 1; i < 5; i++) {
                        // System.out.println("SLOT NUMBER " + i);
                        // }
                        List<Slot> slots = customer.viewSlot(choice2);
                        if (slots.isEmpty()) {
                            System.out.println("No slots available for the selected gym center.");
                            continue;
                        }
                        System.out.println("Here are the available slots!");

                        for (Slot slot : slots) {
                            LocalTime startTime = slot.getStartTime();
                            LocalTime endTime = startTime.plusHours(1);
                            System.out.println("Slot: " + startTime + " - " + endTime + " on " + slot.getDate());
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
                    System.out.println("You can enter the booking number number to CANCEL or EDIT it!");
                    System.out.println("Enter 0 to go back!");

                    int choice5 = scanner.nextInt();
                    scanner.nextLine();

                    if (choice5 == 0) {
                        break;
                    } else {
                        System.out.println("---------------------------------------------");
                        System.out.println("Enter C for CANCEL or E for EDIT (anything else to quit to home page)!");
                        char choice6 = scanner.nextChar();
                        switch (choice6) {
                            case 'C':
                                customer.cancelBooking(choice5);
                                break;
                            case 'E':
                                customer.editBooking(choice5);
                            default:
                                break;
                        }
                    }
                    break;

                case 3:
                    System.out.println("---------------------------------------------");
                    customer.editProfile(null);
                    break;

                case 4:
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
