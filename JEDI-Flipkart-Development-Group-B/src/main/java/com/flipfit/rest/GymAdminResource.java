package com.flipfit.rest;

import com.flipfit.bean.GymCenter;
import com.flipfit.business.GymAdminBusinessService;
import com.flipfit.business.GymAdminBusinessServiceInterface;
import javax.ws.rs.DELETE;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.flipfit.bean.Payment;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymCustomer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipfit.exception.GymNotFoundException;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;

/**
 * This Resource class handles all API endpoints related to Admin functionalities.
 */
@Path("/admin") // Sets the base URL for all methods in this class to "/admin"
@Produces(MediaType.APPLICATION_JSON) // Sets the default response format to JSON
public class GymAdminResource {

    // A variable to hold our business logic
    private final GymAdminBusinessServiceInterface adminBusinessService;

    // The constructor initializes the business service.
    public GymAdminResource() {
        this.adminBusinessService = new GymAdminBusinessService();
    }

    /**
     * This method handles GET requests to the "/admin/gyms" URL.
     * It fetches and returns a list of all gym centers.
     *
     * @return A list of GymCenter objects, which Dropwizard will convert to a JSON array.
     */
    @GET
    @Path("/gyms")
    public List<GymCenter> viewRegisteredGyms() {
        System.out.println("GET /admin/gyms endpoint was hit!"); // A log for our console
        return adminBusinessService.viewRegisteredGyms();
    }
    @GET
    @Path("/payments")
    public List<Payment> viewAllPayments() {
        System.out.println("GET /admin/payments endpoint was hit!"); // A log for our console
        return adminBusinessService.viewPayments();
    }

    @GET
    @Path("/owners")
    public List<GymOwner> viewAllOwners() {
        System.out.println("GET /admin/owners endpoint was hit!"); // A log for our console
        return adminBusinessService.getAllOwners();
    }

    @GET
    @Path("/customers")
    public List<GymCustomer> viewAllCustomers() {
        System.out.println("GET /admin/customers endpoint was hit!"); // A log for our console
        return adminBusinessService.getAllCustomer();
    }

    @DELETE
    @Path("/customers/{customerId}")
    public Response removeCustomer(@PathParam("customerId") int customerId) {
        System.out.println("DELETE /admin/customers/" + customerId + " endpoint was hit!"); // A log for our console

        try {
            boolean isRemoved = adminBusinessService.removeGymCustomer(customerId);

            if (isRemoved) {
                // If the business logic returns true, send a 200 OK with a success message.
                return Response.ok("{\"message\": \"Customer with ID " + customerId + " was successfully removed.\"}").build();
            } else {
                // If the business logic returns false, it means the customer was not found.
                // Send a 404 Not Found error.
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Customer with ID " + customerId + " not found.\"}")
                        .build();
            }
        } catch (Exception e) {
            // Catch any other unexpected database errors.
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred while removing the customer.\"}")
                    .build();
        }
    }
    @DELETE
    @Path("/owners/{ownerId}")
    public Response removeOwner(@PathParam("ownerId") int ownerId) {
        System.out.println("DELETE /admin/owners/" + ownerId + " endpoint was hit!");

        try {
            boolean isRemoved = adminBusinessService.removeGymOwner(ownerId);

            if (isRemoved) {
                return Response.ok("{\"message\": \"Owner with ID " + ownerId + " was successfully removed.\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Owner with ID " + ownerId + " not found.\"}")
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred while removing the owner.\"}")
                    .build();
        }
    }
    // START of Replacement PUT Method

    @PUT
    @Path("/gyms/{gymId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGymApprovalStatus(@PathParam("gymId") int gymId, GymCenter gymUpdate) {
        System.out.println("PUT /admin/gyms/" + gymId + " endpoint hit.");

        try {
            // Check if the gymUpdate object was created successfully.
            // This is our primary safety check.
            if (gymUpdate == null) {
                System.err.println("Error: The incoming JSON could not be mapped to a GymCenter object.");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"Invalid or empty JSON body provided.\"}")
                        .build();
            }

            int newStatus = gymUpdate.getApprovalStatus();
            int verificationResult = adminBusinessService.verifyGymCenter(gymId, newStatus);

            if (verificationResult == -1) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"Invalid status code in JSON. Use 0 for Reject or 1 for Approve.\"}")
                        .build();
            }

            String action = (newStatus == 1) ? "Approved" : "Rejected";
            return Response.ok("{\"message\": \"Gym Center " + gymId + " has been successfully " + action + ".\"}")
                    .build();

        } catch (GymNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred.\"}")
                    .build();
        }
    }

// END of Replacement PUT Method



}