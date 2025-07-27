package com.flipfit.rest;

import com.flipfit.bean.*;
import com.flipfit.business.GymCustomerBusinessService;
import com.flipfit.business.GymCustomerBusinessServiceInterface;
import com.flipfit.business.GymUserBusinessService;
import com.flipfit.business.GymUserBusinessServiceInterface;
import com.flipfit.exception.SlotFullException;
import com.flipfit.exception.UsernameAlreadyExistsException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This Resource class handles all API endpoints related to Customer functionalities.
 */
@Path("/customer") // Sets the base URL for this class to "/customer"
@Produces(MediaType.APPLICATION_JSON)
public class GymCustomerResource {

    private final GymCustomerBusinessServiceInterface customerBusinessService;
    private final GymUserBusinessServiceInterface userBusinessService;
    public GymCustomerResource() {
        this.customerBusinessService = new GymCustomerBusinessService();
        this.userBusinessService = new GymUserBusinessService();
    }

    /**
     * This method handles GET requests to the "/customer/gyms" URL.
     * It fetches and returns a list of all APPROVED gym centers.
     *
     * @return A list of approved GymCenter objects, converted to a JSON array.
     */
    @GET
    @Path("/gyms")
    public List<GymCenter> viewApprovedGyms() {
        System.out.println("GET /customer/gyms endpoint was hit!"); // A log for our console
        return customerBusinessService.viewGymCenter(); // This method already returns only approved centers
    }

    @GET
    @Path("/gyms/{gymId}/slots")
    public List<Slot> viewSlotsForGym(@PathParam("gymId") int gymId) {
        System.out.println("GET /customer/gyms/" + gymId + "/slots endpoint was hit!"); // A log for our console
        return customerBusinessService.viewSlotsFromCenter(gymId);
    }

    @GET
    @Path("/{customerId}/bookings")
    public List<Booking> viewMyBookings(@PathParam("customerId") int customerId) {
        System.out.println("GET /customer/" + customerId + "/bookings endpoint was hit!"); // A log for our console
        return customerBusinessService.viewBookings(customerId);
    }
    @DELETE
    @Path("/bookings/{bookingId}")
    public Response cancelBooking(@PathParam("bookingId") int bookingId) {
        System.out.println("DELETE /customer/bookings/" + bookingId + " endpoint was hit!");

        try {
            // The underlying DAO method for this is a `void` method.
            // It doesn't return a boolean, so we assume success if no exception is thrown.
            customerBusinessService.cancelBooking(bookingId);

            return Response.ok("{\"message\": \"Booking with ID " + bookingId + " was successfully cancelled.\"}")
                    .build();

        } catch (Exception e) {
            // Catch any unexpected database errors.
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred while cancelling the booking.\"}")
                    .build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerCustomer(GymCustomer customer) {
        try {
            // 1. Create the base GymUser
            GymUser newUser = userBusinessService.createUserBean(
                    customer.getName(),
                    customer.getPassword(),
                    0, // Role for Customer
                    customer.getUserName()
            );
            userBusinessService.addUser(newUser);

            // 2. Create the specific GymCustomer details
            customer.setUserId(newUser.getUserId());
            customerBusinessService.registerCustomer(customer);

            // On success, return 201 Created.
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Customer registered successfully!\"}")
                    .build();

        } catch (UsernameAlreadyExistsException e) {
            // If the username is taken, return 409 Conflict.
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred during customer registration.\"}")
                    .build();
        }
    }
    @POST
    @Path("/bookings")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response bookSlot(Booking booking) {
        System.out.println("POST /customer/bookings endpoint was hit!");

        try {
            int customerId = booking.getCustomerId();
            int slotId = booking.getSlotId();

            int newBookingId = customerBusinessService.bookSlot(customerId, slotId);

            // Return 201 Created on success, along with the ID of the new booking.
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Slot booked successfully. Your new booking ID is " + newBookingId + ". Please make a payment to confirm.\"}")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred while booking the slot.\"}")
                    .build();
        }
    }
    @POST
    @Path("/payments")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makePayment(Booking booking) {
        System.out.println("POST /customer/payments endpoint was hit!");

        try {
            int bookingId = booking.getBookingId();

            // This calls our transactional, database-driven business logic.
            boolean paymentSuccess = customerBusinessService.makePayment(bookingId);

            if (paymentSuccess) {
                return Response.ok("{\"message\": \"Payment successful! Your booking with ID " + bookingId + " is confirmed.\"}")
                        .build();
            } else {
                // This case might occur if there's a non-exception failure, though it's less likely now.
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"Payment failed for an unknown reason.\"}")
                        .build();
            }

        } catch (SlotFullException e) {
            // We catch the specific exception for when the slot is full.
            // 409 Conflict is a great HTTP status for this business rule violation.
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred while processing the payment.\"}")
                    .build();
        }
    }

}