package com.flipfit.client;

import com.flipfit.bean.Booking;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Notification;
import com.flipfit.bean.Slot;
import com.flipfit.business.GymCustomerBusinessService;
import com.flipfit.business.GymCustomerBusinessServiceInterface;
import com.flipfit.exception.SlotFullException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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

            try{
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
                                            try {
                                                boolean paymentSuccess = customerBusiness.makePayment(bookingId);
                                                // If the method completes without an exception, it means success.
                                                if (paymentSuccess) {
                                                    System.out.println("\n[SUCCESS] Payment made! Your booking is now confirmed (Booking ID: " + bookingId + ").\n");
                                                } else {
                                                    // This else block can handle other potential, non-exception failures if any exist.
                                                    System.out.println("\n[ERROR] An unknown error occurred during payment. Please try again.\n");
                                                }
                                            } catch (SlotFullException e) {
                                                // Catch the specific exception for a full slot.
                                                System.out.println("\n[ERROR] " + e.getMessage() + "\n");
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

                        if (!bookingIds.isEmpty()) {
                            System.out.print("\n> To manage a booking, enter its ID. (Enter 0 to return to the main menu): ");
                            int selectedBookingId = scanner.nextInt();
                            scanner.nextLine();

                            if (selectedBookingId == 0) {
                                // User chose to go back. The loop will continue to the main menu.
                            } else if (bookingIds.contains(selectedBookingId)) {
                                // Find the selected booking object to check its status.
                                Booking selectedBooking = null;
                                for (Booking booking : bookingResultsList) {
                                    if (booking.getBookingId() == selectedBookingId) {
                                        selectedBooking = booking;
                                        break;
                                    }
                                }

                                // Default action is to cancel.
                                System.out.print("> Select an action: (C)ancel Booking");

                                // THIS IS THE NEW LOGIC:
                                // Only show the "Pay" option if the booking is pending.
                                if (selectedBooking != null && selectedBooking.getStatus() == 0) {
                                    System.out.print(" or (P)ay for this Booking");
                                }
                                System.out.print(". (Any other key to go back): ");

                                char actionChoice = scanner.next().toUpperCase().charAt(0);
                                scanner.nextLine(); // consume newline

                                switch (actionChoice) {
                                    case 'C':
                                        customerBusiness.cancelBooking(selectedBookingId);
                                        System.out.printf("\n[SUCCESS] Booking %d has been cancelled.\n", selectedBookingId);
                                        break;

                                    case 'P':
                                        // Check again that the booking is valid and pending before trying to pay.
                                        if (selectedBooking != null && selectedBooking.getStatus() == 0) {
                                            try {
                                                boolean paymentSuccess = customerBusiness.makePayment(selectedBookingId);
                                                if (paymentSuccess) {
                                                    System.out.println("\n[SUCCESS] Payment made! Your booking is now confirmed.\n");
                                                } else {
                                                    System.out.println("\n[ERROR] An unknown error occurred during payment.\n");
                                                }
                                            } catch (SlotFullException e) {
                                                System.out.println("\n[ERROR] " + e.getMessage() + "\n");
                                            }
                                        } else {
                                            // This handles the case where the user typed 'P' for a confirmed booking.
                                            System.out.println("\n[INFO] This booking does not require payment. Returning to menu.\n");
                                        }
                                        break;

                                    default:
                                        System.out.println("\nReturning to the main menu.\n");
                                        break;
                                }
                            } else {
                                System.out.println("\n[ERROR] Invalid Booking ID. Please try again.\n");
                            }
                        }

                        break;

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
            } catch (InputMismatchException e) {
                System.out.println("\n[ERROR] Invalid input. Please enter a number.\n");
                scanner.nextLine(); // Clear the bad input
            }
        }

        scanner.close();

 }

}
