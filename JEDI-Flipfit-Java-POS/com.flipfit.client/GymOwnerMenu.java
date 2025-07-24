package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GymOwnerMenu {
    public static void ownerMenu(int ownerId) {
        GymOwnerBusinessServiceInterface ownerBusiness = new GymOwnerBusinessService();
        Boolean isRunning = true;

        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            System.out.println("---------------------------------------------");
            System.out.println("Welcome Gym Owner!");
            System.out.println("Enter your choice");
            System.out.println("\t1\tRegister Gym Center");
            System.out.println("\t2\tView Gym Centers");
            System.out.println("\t3\tView Slot");// this option allows view bookings , payments and edit slot option
            System.out.println("\t4\tAdd Slots");
            System.out.println("\t5\tView Notifications");
            System.out.println("\t6\tView all Payments for a Gym Center");
            System.out.println("\t7\tEdit Slot");
            System.out.println("\t8\tLogout");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("---------------------------------------------");
                    System.out.println("Enter Center Name:");
                    String centerName = scanner.nextLine();
                    scanner.nextLine();
                    System.out.println("Enter Center Location:");
                    String centerLocation = scanner.nextLine();
                    scanner.nextLine();
                    System.out.println("Enter Gym Capacity:");
                    int capacity = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Number of Slots:");
                    int numSlots = scanner.nextInt();
                    scanner.nextLine();
                    GymCenter newgymCenter = ownerBusiness.createGymCenterBean(centerName, centerLocation, capacity,
                            numSlots, ownerId);
                    ownerBusiness.registerGymCenter(newgymCenter);
                    System.out.println("Your gym has been sent for approval!");
                    System.out.println("---------------------------------------------");
                    break;
                case 2:
                    System.out.println("---------------------------------------------");
                    List<GymCenter> gymCenters = ownerBusiness.viewGymCenters(ownerId);
                    if (gymCenters.isEmpty()) {
                        System.out.println("No gym centers found for this owner.");
                    } else {
                        System.out.println("Gym Centers for you:");
                        for (GymCenter center : gymCenters) {
                            System.out.println("Center Name: " + center.getName() + "Center Id: " + center.getCenterId()
                                    + " Location: " + center.getLocation());
                            System.out.println("Gym Capacity: " + center.getCapacity() + "Total Slots: " + center.getNumSlots());
                            if(center.getApprovalStatus()==0)
                            {
                                System.out.println("Your approval status is:  Rejected!");
                            }
                            else if (center.getApprovalStatus()==1)
                            {
                                System.out.println("Your approval status is:  Approved!");
                            }
                            else if (center.getApprovalStatus()==2)
                            {
                                System.out.println("Your approval status is:  Pending!");
                            }
                        }
                    }
                    System.out.println("---------------------------------------------");
                    break;
                case 3:
                    System.out.println("---------------------------------------------");
                    System.out.println("Enter Center Id:");
                    int centerId1 = scanner.nextInt();
                    scanner.nextLine();
                    List<Slot> slotResultsList = ownerBusiness.viewSlots(centerId1);
                    List<Integer> slotIds = new ArrayList<>();
                    if (slotResultsList.isEmpty()) {
                        System.out.println("No slots available for the selected gym center.");
                    } else {
                        System.out.println("Here are the available slots!");
                        for (Slot slot : slotResultsList) {
                            slotIds.add(slot.getSlotId());
                        }
                        for (int i = 0; i < slotResultsList.size(); i++) {
                            System.out.println("Slot ID: " + slotIds.get(i));
                            LocalTime slotStartTime = slotResultsList.get(i).getStartTime();
                            LocalTime slotEndTime = slotStartTime.plusHours(1);
                            LocalDate slotDate = slotResultsList.get(i).getDate();
                            System.out.println("Slot: " + slotStartTime + " - " + slotEndTime + " on " + slotDate);
                            System.out.println("---------------------------------------------");
                        }
                    }
//                    if (slotResultsList.isEmpty()) {
//                        System.out.println("No slots available for the selected gym center.");
//                    } else {
//                        System.out.println("Here are the available slots!");
//                        List<Integer> slotIds = (List<Integer>) slotResultsList.get(0);
//                        List<Slot> slots = (List<Slot>) slotResultsList.get(1);
//                        for (int i = 0; i < slotIds.size(); i++) {
//                            System.out.println("Slot ID: " + slotIds.get(i));
//                            LocalTime slotStartTime = slots.get(i).getStartTime();
//                            LocalTime slotEndTime = slotStartTime.plusHours(1);
//                            LocalDate slotDate = slots.get(i).getDate();
//                            System.out.println("Slot: " + slotStartTime + " - " + slotEndTime + " on " + slotDate);
//                            System.out.println("---------------------------------------------");
//                        }
//                    }
                    System.out.println("---------------------------------------------");
                    System.out.println("Enter Slot ID to see bookings data or to exit enter -1");
                    int slotId = scanner.nextInt();
                    scanner.nextLine();
                    if (slotId == -1) {
                        System.out.println("Exiting slot view.");
                        System.out.println("---------------------------------------------");
                        break;
                    }
                    List<Booking> bookings = ownerBusiness.viewBookingDetails(slotId);
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings found for this slot.");
                    } else {
                        System.out.println("Bookings for this slot:");

                        for (Booking booking : bookings) {
                            System.out.println("Booking ID: " + booking.getBookingId() + ", Customer ID: "
                                    + booking.getCustomerId() );
                            if(booking.getStatus()==1)
                            {
                                Payment payment = ownerBusiness.viewPaymentDetails(booking.getBookingId());
                                System.out.println("Payment Status: Confirmed");
                                System.out.println("Payment ID: " + payment.getPaymentId() + ", Amount: "
                                        + payment.getAmount() + ", Payment Date: " + payment.getPaymentDateTime());
                            }
                            else {
                                System.out.println("Payment Status: Pending");
                            }
                        }
                    }
                    System.out.println("---------------------------------------------");
                    break;

                case 4:
                    System.out.println("---------------------------------------------");
                    System.out.println("Enter Center Id:");
                    int centerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter a date (yyyy-MM-dd): ");
                    String dateInput = scanner.nextLine();
                    scanner.nextLine();
                    LocalDate userDate = LocalDate.parse("2025-07-28");
                    try {
                        LocalDate userDate1 = LocalDate.parse(dateInput);
                        userDate = userDate1;
//                        System.out.println("You entered: " + userDate1);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                    }
                    System.out.print("Enter time (e.g., HH:mm): ");
                    String timeString = scanner.nextLine();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime userTime = LocalTime.parse("00:00",formatter);
                    try {
                        LocalTime userTime1 = LocalTime.parse(timeString, formatter);
                        userTime  = userTime1;
//                        System.out.println("You entered: " + userTime1);
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Invalid time format. Please use HH:mm.");
                    }
                    Slot newSlot = ownerBusiness.createSlotBean(userTime,userDate,centerId);
                    ownerBusiness.registerGymSlot(newSlot);
                    System.out.println("Your gym slot been added successfully.!");
                    System.out.println("---------------------------------------------");
                    break;
                case 5:
                    System.out.println("---------------------------------------------");
                    System.out.println("Notifications:");
                    List<Notification> notifications = ownerBusiness.viewNotificationsByOwnerId(ownerId);
                    for (Notification notification : notifications) {
                        System.out.println(notification.getMessage());
                    }
                    if (notifications.isEmpty()) {
                        System.out.println("No notifications available.");
                    }
                    System.out.println("---------------------------------------------");
                    break;
                case 6:
                    System.out.println("---------------------------------------------");
                    System.out.println("Enter Center Id:");
                    int centerId2 = scanner.nextInt();
                    scanner.nextLine();
                    List<Payment> payments = ownerBusiness.viewAllPayments(centerId2);
                    float totalIncome = 0;
                    for (Payment payment : payments) {
                        System.out.println("Payment ID: " + payment.getPaymentId() +  ", Payment Date: " +
                                payment.getPaymentDateTime() + "Customer ID: " + payment.getCustomerId() + "," +
                                " Amount: " + payment.getAmount() );
                        totalIncome += payment.getAmount();
                    }
                    System.out.println("Total Income: " + totalIncome);
                    System.out.println("---------------------------------------------");
                    break;
                case 7:
                    ownerBusiness.editSlot(23, "HSR");
                    break;
                case 8:
                    System.out.println("Logging out...");
                    isRunning = false; // Exit the owner menu
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }
}
