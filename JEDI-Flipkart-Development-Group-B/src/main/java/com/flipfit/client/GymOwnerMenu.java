package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.GymOwnerBusinessService;
import com.flipfit.business.GymOwnerBusinessServiceInterface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


/*
 *@author:Eshwar, Ritesh, Zaid
 *@ClassName:GymOwnerMenu
 *@version:1.0
 *@See :GymOwnerBusinessServiceInterface
 */
public class GymOwnerMenu {
    public static void ownerMenu(int ownerId) {
        GymOwnerBusinessServiceInterface ownerBusiness = new GymOwnerBusinessService();
        Boolean isRunning = true;

        Scanner scanner = new Scanner(System.in);

        while (isRunning) {

                System.out.printf("%n*********************************************%n");
                System.out.printf("            GYM OWNER DASHBOARD%n");
                System.out.printf("*********************************************%n");

                System.out.println("\n--- Center Management ---");
                System.out.println("  1. Register a New Gym Center");
                System.out.println("  2. View Your Gym Centers");

                System.out.println("\n--- Slot & Booking Management ---");
                System.out.println("  3. View Slots, Bookings and Payments by Center");
                System.out.println("  4. Add New Slots to a Center");

                System.out.println("\n--- Account & Finance ---");
                System.out.println("  5. View Notifications");
                System.out.println("  6. View All Payments for a Center");


                System.out.println("  7. Logout");

                System.out.print("\nEnter your choice -> ");

            try{
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.printf("%n****************** REGISTER A NEW GYM CENTER ******************%n");

                        System.out.print("> Enter Center Name: ");
                        String centerName = scanner.nextLine();

                        System.out.print("> Enter Center Location (e.g., 'HSR', 'Koramangala'): ");
                        String centerLocation = scanner.nextLine();

                        System.out.print("> Enter Total Gym Capacity (number of people): ");
                        int capacity = scanner.nextInt();
                        scanner.nextLine(); // Consume newline after reading an integer

                        System.out.print("> Enter Number of Slots to be offered: ");
                        int numSlots = scanner.nextInt();
                        scanner.nextLine(); // Consume newline after reading an integer

                        GymCenter newgymCenter = ownerBusiness.createGymCenterBean(centerName, centerLocation, capacity, numSlots, ownerId);
                        ownerBusiness.registerGymCenter(newgymCenter);

                        System.out.println("\n[SUCCESS] Your gym center registration has been submitted for admin approval.");
                        System.out.println("           You can check its status from the 'View Your Gym Centers' option.");
                        break;

                    case 2:
                        System.out.printf("%n****************** YOUR REGISTERED GYM CENTERS ******************%n");
                        List<GymCenter> gymCenters = ownerBusiness.viewGymCenters(ownerId);

                        if (gymCenters.isEmpty()) {
                            System.out.println("\n-- You have not registered any gym centers yet. --\n");
                        } else {
                            for (GymCenter center : gymCenters) {
                                String status;
                                switch (center.getApprovalStatus()) {
                                    case 0:
                                        status = "Rejected";
                                        break;
                                    case 1:
                                        status = "Approved";
                                        break;
                                    default:
                                        status = "Pending Approval";
                                        break;
                                }

                                System.out.println("\n--------------------------------------------------");
                                System.out.printf("  %-15s : %s (ID: %d)\n", "GYM NAME", center.getName(), center.getCenterId());
                                System.out.printf("  %-15s : %s\n", "LOCATION", center.getLocation());
                                System.out.printf("  %-15s : %d\n", "CAPACITY", center.getCapacity());
                                System.out.printf("  %-15s : %d\n", "TOTAL SLOTS", center.getNumSlots());
                                System.out.printf("  %-15s : %s\n", "STATUS", status);
                                System.out.println("--------------------------------------------------");
                            }
                        }
                        break;

                    case 3:
                        System.out.printf("%n****************** VIEW SLOTS & BOOKINGS ******************%n");
                        System.out.print("> Enter the Center ID to view its slots: ");
                        int centerId1 = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        List<Slot> slotResultsList = ownerBusiness.viewSlots(centerId1);
                        List<Integer> slotIds = new ArrayList<>();

                        System.out.printf("%n----------- SLOTS for Center ID: %d -----------%n", centerId1);
                        if (slotResultsList.isEmpty()) {
                            System.out.println("\n-- No slots have been added for this gym center yet. --\n");
                        } else {
                            System.out.printf("----------------------------------------------------%n");
                            System.out.printf("| %-8s | %-15s | %-20s |%n", "SLOT ID", "DATE", "TIME");
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

                        if (!slotIds.isEmpty()) {
                            System.out.print("\n> Enter a Slot ID to view its booking details (or 0 to go back): ");
                            int slotId = scanner.nextInt();
                            scanner.nextLine(); // consume newline

                            if (slotId != 0) {
                                List<Booking> bookings = ownerBusiness.viewBookingDetails(slotId);
                                System.out.printf("%n---------------- BOOKINGS for Slot ID: %d ----------------%n", slotId);

                                if (bookings.isEmpty()) {
                                    System.out.println("\n-- No bookings have been made for this slot yet. --\n");
                                } else {
                                    System.out.printf("--------------------------------------------------------------------------------------------------------%n");
                                    System.out.printf("| %-10s | %-12s | %-15s | %-12s | %-10s | %-25s |%n", "BOOKING ID", "CUSTOMER ID", "STATUS", "PAYMENT ID", "AMOUNT", "PAYMENT DATE");
                                    System.out.printf("--------------------------------------------------------------------------------------------------------%n");

                                    for (Booking booking : bookings) {
                                        if (booking.getStatus() == 1) { // Confirmed Booking
                                            Payment payment = ownerBusiness.viewPaymentDetails(booking.getBookingId());
                                            System.out.printf("| %-10d | %-12d | %-15s | %-12d | %-10.2f | %-25s |%n",
                                                    booking.getBookingId(),
                                                    booking.getCustomerId(),
                                                    "Confirmed",
                                                    payment.getPaymentId(),
                                                    payment.getAmount(),
                                                    payment.getPaymentDateTime().toString());
                                        } else { // Pending Booking
                                            System.out.printf("| %-10d | %-12d | %-15s | %-12s | %-10s | %-25s |%n",
                                                    booking.getBookingId(),
                                                    booking.getCustomerId(),
                                                    "Pending",
                                                    "N/A",
                                                    "N/A",
                                                    "N/A");
                                        }
                                    }
                                    System.out.printf("--------------------------------------------------------------------------------------------------------%n");
                                }
                            }
                        }
                        break; // This is the main break for case 3

                    case 4:
                        System.out.printf("%n********************* ADD A NEW SLOT *********************%n");

                        System.out.print("> Enter the Center ID to add a slot to: ");
                        int centerId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        LocalDate userDate;
                        LocalTime userTime;

                        // --- Get and Validate Date ---
                        System.out.print("> Enter the slot date (in YYYY-MM-DD format): ");
                        String dateInput = scanner.nextLine();
                        try {
                            userDate = LocalDate.parse(dateInput);
                        } catch (DateTimeParseException e) {
                            System.out.println("\n[ERROR] Invalid date format. Please use YYYY-MM-DD. Action cancelled.\n");
                            break; // Exit the case if the date is invalid
                        }

                        // --- Get and Validate Time ---
                        System.out.print("> Enter the slot start time (in 24-hour HH:MM format): ");
                        String timeString = scanner.nextLine();
                        try {
                            userTime = LocalTime.parse(timeString);
                        } catch (DateTimeParseException e) {
                            System.out.println("\n[ERROR] Invalid time format. Please use HH:MM. Action cancelled.\n");
                            break; // Exit the case if the time is invalid
                        }

                        // --- Create and Register Slot ---
                        Slot newSlot = ownerBusiness.createSlotBean(userTime, userDate, centerId);
                        ownerBusiness.registerGymSlot(newSlot);

                        System.out.printf("\n[SUCCESS] A new slot for %s at %s has been added to Center ID %d.\n", userDate, userTime, centerId);
                        break;

                    case 5:
                        System.out.printf("%n********************* YOUR NOTIFICATIONS *********************%n");
                        List<Notification> notifications = ownerBusiness.viewNotificationsByOwnerId(ownerId);

                        if (notifications.isEmpty()) {
                            System.out.println("\n-- You have no new notifications. --\n");
                        } else {
                            System.out.println(); // Add a blank line for spacing
                            for (Notification notification : notifications) {
                                // Using a "bullet point" style for each message
                                System.out.println("  -> " + notification.getMessage());
                            }
                            System.out.println(); // Add another blank line for spacing
                        }

                        System.out.printf("************************************************************%n\n");
                        break;

                    case 6:
                        System.out.printf("%n****************** VIEW PAYMENTS BY CENTER ******************%n");
                        System.out.print("> Enter the Center ID to view its payment history: ");
                        int centerId2 = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        List<Payment> payments = ownerBusiness.viewAllPayments(centerId2);

                        System.out.printf("%n----------- PAYMENT REPORT for Center ID: %d -----------%n", centerId2);

                        if (payments.isEmpty()) {
                            System.out.println("\n-- No payments have been recorded for this center yet. --\n");
                        } else {
                            float totalIncome = 0;
                            System.out.printf("--------------------------------------------------------------------------------%n");
                            System.out.printf("| %-12s | %-12s | %-12s | %-25s |%n", "PAYMENT ID", "CUSTOMER ID", "AMOUNT", "DATE & TIME");
                            System.out.printf("--------------------------------------------------------------------------------%n");

                            for (Payment payment : payments) {
                                System.out.printf("| %-12d | %-12d | %-12.2f | %-25s |%n",
                                        payment.getPaymentId(),
                                        payment.getCustomerId(),
                                        payment.getAmount(),
                                        payment.getPaymentDateTime().toString());
                                totalIncome += payment.getAmount();
                            }
                            System.out.printf("--------------------------------------------------------------------------------%n");
                            System.out.printf("\n  %-20s: %.2f\n\n", "TOTAL INCOME", totalIncome);
                        }
                        break;

                    case 7:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n[ERROR] Invalid input. Please enter a number.\n");
                scanner.nextLine(); // Clear the bad input from the scanner
            }
        }
        scanner.close();
    }
}
