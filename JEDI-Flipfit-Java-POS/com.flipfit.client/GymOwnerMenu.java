package com.flipfit.client;

import com.flipfit.bean.*;
import com.flipfit.business.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

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
            System.out.println("\t3\tAdd Slots and Capacity");
            System.out.println("\t4\tView Slot");// this option allows view bookings , payments and edit slot options
            System.out.println("\t5\tView Notifications");
            System.out.println("\t6\tLogout");

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
                    break;
                case 2:
                    List<GymCenter> gymCenters = ownerBusiness.viewGymCenters(ownerId);
                    if (gymCenters.isEmpty()) {
                        System.out.println("No gym centers found for this owner.");
                    } else {
                        System.out.println("Gym Centers for you:");
                        for (GymCenter center : gymCenters) {
                            System.out.println("Center Name: " + center.getName() + "Center Id: " + center.getCenterId()
                                    + " Location: " + center.getLocation());
                        }
                    }
                    break;
                case 3:
                    System.out.println("---------------------------------------------");
                    System.out.println("Enter Center Id:");
                    int centerId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Number of Slots:");
                    int numSlots1 = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Capacity:");
                    int capacity1 = scanner.nextInt();
                    scanner.nextLine();
                    ownerBusiness.addSlotsAndCapacity(centerId, numSlots1, capacity1);
                    System.out.println("Slots and capacity added for center: " + centerId);
                    break;
                case 4:
                    System.out.println("---------------------------------------------");
                    System.out.println("Enter Center Id:");
                    int centerId1 = scanner.nextInt();
                    scanner.nextLine();
                    List<Object> slotResultsList = ownerBusiness.viewSlots(centerId1);
                    if (slotResultsList.isEmpty()) {
                        System.out.println("No slots available for the selected gym center.");
                    } else {
                        System.out.println("Here are the available slots!");
                        List<Integer> slotIds = (List<Integer>) slotResultsList.get(0);
                        List<Slot> slots = (List<Slot>) slotResultsList.get(1);
                        for (int i = 0; i < slotIds.size(); i++) {
                            System.out.println("Slot ID: " + slotIds.get(i));
                            LocalTime slotStartTime = slots.get(i).getStartTime();
                            LocalTime slotEndTime = slotStartTime.plusHours(1);
                            LocalDate slotDate = slots.get(i).getDate();
                            System.out.println("Slot: " + slotStartTime + " - " + slotEndTime + " on " + slotDate);
                            System.out.println("---------------------------------------------");
                        }
                    }
                    System.out.println("---------------------------------------------");
                    System.out.println("Enter Slot ID to see bookings data or to exit enter -1");
                    int slotId = scanner.nextInt();
                    scanner.nextLine();
                    if (slotId == -1) {
                        System.out.println("Exiting slot view.");
                        break;
                    }
                    List<Booking> bookings = ownerBusiness.viewBookingDetails(slotId);
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings found for this slot.");
                    } else {
                        System.out.println("Bookings for this slot:");

                        for (Booking booking : bookings) {
                            System.out.println("Booking ID: " + booking.getBookingId() + ", Customer ID: "
                                    + booking.getCustomerId() + ", Status: " + booking.getStatus());
                        }
                    }
                    break;
                case 5:
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
                    ownerBusiness.viewPayment(new GymCenter());
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
