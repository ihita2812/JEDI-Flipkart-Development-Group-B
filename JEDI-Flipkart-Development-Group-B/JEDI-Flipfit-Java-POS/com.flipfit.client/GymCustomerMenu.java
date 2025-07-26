package com.flipfit.client;

import java.util.*;
import com.flipfit.business.*;
import com.flipfit.bean.*;
import java.time.LocalTime;
import java.time.LocalDate;

/*
 *@author:Kashif, Zaid
 *@ClassName:GymCustomerMenu
 *@version:1.0
 *@See :GymCustomerBusinessServiceInterface
 */
public class GymCustomerMenu {

    public static void customerMenu(int customerId) {

        GymCustomerBusinessServiceInterface customerBusiness = new GymCustomerBusinessService();

        Scanner scanner = new Scanner(System.in);
        boolean more = true;

        while (more) {
            System.out.printf("*********************************************%n");
            System.out.printf("           CUSTOMER DASHBOARD%n");
            System.out.printf("*********************************************%n");
            System.out.println("Welcome, Customer!");
            System.out.println("\nWhat would you like to do today?");
            System.out.println("  1. View & Book Gym Slots");
            System.out.println("  2. Manage Your Bookings");
            System.out.println("  3. Check Notifications");
            System.out.println("  4. Logout");
            System.out.print("\nEnter your choice -> ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {

            case 1:
                System.out.printf("--------------------------------------------------%n");
                System.out.printf("                Gym centers for you               %n");
                System.out.printf("--------------------------------------------------%n");
                System.out.printf("| %-9s | %-16s | %15s |%n", "CENTER ID", "NAME", "LOCATION");
                System.out.printf("--------------------------------------------------%n");

                List<GymCenter> centerList = customerBusiness.viewGymCenter();
                List<Integer> centerIds = new ArrayList<>();
                for (GymCenter gymCenter : centerList) {
                    centerIds.add(gymCenter.getCenterId());
                    System.out.printf("| %-8d | %-16s | %-15s |%n", gymCenter.getCenterId(), gymCenter.getName(), gymCenter.getLocation());
                    System.out.printf("--------------------------------------------------%n");
                }
                System.out.println("You can enter the gym center number to view available slots!");
                System.out.println("Enter 0 to go to home page!");

                int choice2 = scanner.nextInt();
                scanner.nextLine();

                if (choice2 == 0) {
                    break;
                } else {
                    if (centerIds.contains(choice2)) {
                        System.out.println("---------------------------------------------");
                        List<Slot> slotResultsList = customerBusiness.viewSlotsFromCenter(choice2);
                        List<Integer> slotIds = new ArrayList<>();

                        if (slotResultsList.isEmpty()) {
                            System.out.println("\n-- No slots currently available for this gym center. --\n");
                        } else {
                            String centerName = customerBusiness.viewCenterName(choice2); // Get center name for the header
                            System.out.printf("%n----------- AVAILABLE SLOTS for %s -----------%n", centerName.toUpperCase());
                            System.out.printf("----------------------------------------------------%n");
                            System.out.printf("| %-8s | %-15s | %-20s |%n", "SLOT ID", "DATE", "SLOT TIME");
                            System.out.printf("----------------------------------------------------%n");

                            for (Slot slot : slotResultsList) {
                                slotIds.add(slot.getSlotId());
                                LocalTime startTime = slot.getStartTime();
                                LocalTime endTime = startTime.plusHours(1);
                                String timeRange = startTime + " - " + endTime;

                                System.out.printf("| %-8d | %-15s | %-20s |%n",
                                        slot.getSlotId(),
                                        slot.getDate().toString(),
                                        timeRange);
                            }
                            System.out.printf("----------------------------------------------------%n");
                        }

                        System.out.println("---------------------------------------------");
                        System.out.println("You can enter the slot number to book it!");
                        System.out.println("Enter 0 to go to home page!");

//                        int choice3 = scanner.nextInt();
//                        scanner.nextLine();
//
//                        if (choice3 == 0) {
//                            break;
//                        } else {
//                            if (slotIds.contains(choice3)) {
//                                System.out.println("---------------------------------------------");
//                                int bookingId = customerBusiness.bookSlot(customerId, choice3);
//                                System.out.println("Slot booked successfully! Your booking ID is: " + bookingId);
//                                System.out.println("You can make payment now or later.");
//                                System.out.println("Make payment at least 6 hours before the slot timing to confirm!");
//                                System.out.println("Enter 1 to pay now, 0 to pay later and go to home page!");
//
//                                int choice4 = scanner.nextInt();
//                                scanner.nextLine();
//
//                                if (choice4 == 1) {
//                                    System.out.println("---------------------------------------------");
//                                    boolean slotBooked = customerBusiness.makePayment(bookingId);
//                                    if  (slotBooked) {
//                                        System.out.println("Payment made successfully! Your booking (Booking ID:" + bookingId + ") is confirmed.");
//                                    } else {
//                                        System.out.println("Payment not made; slot already filled! Try another slot.");
//                                    }
//                                } else {
//                                    more = false;
//                                }
//                            } else {
//                                System.out.println("Invalid Slot ID. Breaking to main menu.");
//                                break;
//                            }
                        int choice3 = scanner.nextInt();
                        scanner.nextLine();

                        if (choice3 == 0) {
                            break;
                        } else {
                            if (slotIds.contains(choice3)) {
                                int bookingId = customerBusiness.bookSlot(customerId, choice3);

                                System.out.printf("%n++++++++++++++++++++++ BOOKING PENDING ++++++++++++++++++++++%n");
                                System.out.printf("  Slot booked successfully! Your Booking ID is: %d%n", bookingId);
                                System.out.println("  NOTE: Your booking is NOT CONFIRMED until payment is made.");
                                System.out.printf("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++%n");

                                System.out.print("\n> Enter 1 to Pay & Confirm Now, or 0 to Pay Later: ");
                                int choice4 = scanner.nextInt();
                                scanner.nextLine();

                                if (choice4 == 1) {
                                    boolean paymentSuccess = customerBusiness.makePayment(bookingId);
                                    if  (paymentSuccess) {
                                        System.out.println("\n[SUCCESS] Payment made! Your booking is now confirmed (Booking ID: " + bookingId + ").\n");
                                    } else {
                                        System.out.println("\n[ERROR] Payment failed. The slot may have been filled by another user. Please try another slot.\n");
                                    }
                                } else {
                                    System.out.println("\n[INFO] You can pay for your booking later from the 'Manage Your Bookings' menu.\n");
                                    // Setting 'more' to false seems to exit the whole menu. Let's keep the user in the menu.
                                    // more = false;
                                }
                            } else {
                                System.out.println("\n[ERROR] Invalid Slot ID. Breaking to main menu.\n");
                                break;
                            }
                        }
                    } else {
                        System.out.println("[ERROR] Invalid Center ID. Breaking to main menu.");
                    }
                }
            break;

            case 2:
//                    System.out.println("---------------------------------------------");
//                    System.out.println("Here are your bookings!");
//                    List<Booking> bookingResultsList = customerBusiness.viewBookings(customerId);
//                    List<Integer> bookingIds = new ArrayList<>();
//                    if (bookingResultsList.isEmpty()) {
//                        System.out.println("No bookings found.");
//                    } else {
//                        for (Booking booking : bookingResultsList) {
//                            int bookingId = booking.getBookingId();
//                            bookingIds.add(bookingId);
//                            System.out.println("Booking ID: " + bookingId);
//                            Slot bookingSlot = customerBusiness.viewSlotFromBooking(bookingId);
//                            LocalTime slotStartTime = bookingSlot.getStartTime();
//                            LocalTime slotEndTime = slotStartTime.plusHours(1);
//                            LocalDate slotDate = bookingSlot.getDate();
//                            System.out.println("Slot: " + slotStartTime + " - " + slotEndTime + " on " + slotDate);
//                            System.out.println("Status: " + booking.getStatus());
//                            System.out.println("Gym Center: " + customerBusiness.viewCenterName(bookingSlot.getCenterId()));
//                            System.out.println("---------------------------------------------");
//                        }
//                    }
                        System.out.printf("%n********************** YOUR BOOKINGS **********************%n");
                        List<Booking> bookingResultsList = customerBusiness.viewBookings(customerId);
                        List<Integer> bookingIds = new ArrayList<>();

                        if (bookingResultsList.isEmpty()) {
                            System.out.println("\n-- You have no active or pending bookings at the moment. --\n");
                        } else {
                            System.out.printf("------------------------------------------------------------------------------------%n");
                            System.out.printf("| %-10s | %-20s | %-12s | %-17s | %-15s |%n", "BOOKING ID", "GYM CENTER", "DATE", "SLOT", "STATUS");
                            System.out.printf("------------------------------------------------------------------------------------%n");

                            for (Booking booking : bookingResultsList) {
                                int bookingId = booking.getBookingId();
                                bookingIds.add(bookingId);

                                Slot bookingSlot = customerBusiness.viewSlotFromBooking(bookingId);
                                String centerName = customerBusiness.viewCenterName(bookingSlot.getCenterId());

                                LocalTime startTime = bookingSlot.getStartTime();
                                LocalTime endTime = startTime.plusHours(1);
                                String timeRange = startTime + " - " + endTime;

                                LocalDate date = bookingSlot.getDate();

                                String status = booking.getStatus() == 1 ? "Confirmed" : "Pending Payment";

                                System.out.printf("| %-10d | %-20s | %-12s | %-17s | %-15s |%n",
                                        bookingId,
                                        centerName,
                                        date.toString(),
                                        timeRange,
                                        status);
                            }
                        System.out.printf("------------------------------------------------------------------------------------%n");
                    }
//
//                    System.out.println("---------------------------------------------");
//                    System.out.println("You can enter the booking number number to CANCEL or EDIT it!");
//                    System.out.println("Enter 0 to go back!");
//
//                        int choice5 = scanner.nextInt();
//                        scanner.nextLine();
//
//                        if (choice5 == 0) {
//                            break;
//                        } else {
//                            if (bookingIds.contains(choice5)) {
//                            System.out.println("---------------------------------------------");
//                                System.out.println("Enter C for CANCEL or E for EDIT (anything else to quit to home page)!");
//                                char choice6 = scanner.next().charAt(0);
//                                switch (choice6) {
//                                    case 'C':
//                                        customerBusiness.cancelBooking(choice5);
//                                        System.out.println("Booking " + choice5 + " cancelled successfully.");
//                                        break;
//                                    case 'E':
//                                        customerBusiness.editBooking(choice5);
//                                    default:
//                                        break;
//                                }
//                            } else {
//                            System.out.println("Invalid Booking ID. Breaking to main menu.");
//                        }
//
//                    }
//                        break;
                if (!bookingIds.isEmpty()) {
                    System.out.print("\n> To manage a booking, enter its ID. (Enter 0 to return to the main menu): ");
                    int choice5 = scanner.nextInt();
                    scanner.nextLine();

                    if (choice5 == 0) {
                        // User chooses to go back, so we just let the loop continue to the main menu.
                    } else if (bookingIds.contains(choice5)) {
                        System.out.print("> Select an action: (C)ancel Booking or any other key to go back: ");
                        char choice6 = scanner.next().toUpperCase().charAt(0); // Read and convert to uppercase
                        scanner.nextLine(); // consume newline

                        if(choice6 == 'C') {
                            customerBusiness.cancelBooking(choice5);
                            System.out.printf("\n[SUCCESS] Booking %d has been cancelled.\n", choice5);
                        }
                        else
                            System.out.println("\nReturning to the main menu.\n");
                    } else {
                        System.out.println("\n[ERROR] Invalid Booking ID. Please try again.\n");
                    }
                }
                break;

//            case 3:
//            System.out.println("---------------------------------------------");
//            customerBusiness.editProfile(null);
//            break;

            case 3:
                System.out.printf("%n********************* NOTIFICATIONS *********************%n");
                List<Notification> notifications = customerBusiness.viewNotificationsByCustomerId(customerId);

                if (notifications.isEmpty()) {
                    System.out.println("\n-- You have no new notifications. --\n");
                } else {
                    System.out.println(); // Add a blank line for spacing
                    for (Notification notification : notifications) {
                        // Using a "bullet point" style for each message
                        System.out.println("  -> " + notification.getMessage());
                    }
                    System.out.println(); // Add another blank line for spacing before the footer
                }

                System.out.printf("*******************************************************%n\n");
                break;
            case 4:
                System.out.print("Logging Out, Thank you for using flipfit app !!!\n");
                return;

            default:
                System.out.println("---------------------------------------------");
                System.out.println("Bad choice :/");

            }
        }

        scanner.close();

 }

}
