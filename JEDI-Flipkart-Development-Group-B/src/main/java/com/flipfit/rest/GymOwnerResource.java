package com.flipfit.rest;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymUser;
import com.flipfit.business.GymOwnerBusinessService;
import com.flipfit.business.GymOwnerBusinessServiceInterface;
import com.flipfit.business.GymUserBusinessService;
import com.flipfit.business.GymUserBusinessServiceInterface;
import com.flipfit.exception.UsernameAlreadyExistsException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.flipfit.bean.GymCenter;
import com.flipfit.bean.Slot;
import javax.ws.rs.PathParam;

@Path("/owner") // Base URL for all owner endpoints
@Produces(MediaType.APPLICATION_JSON)
public class GymOwnerResource {

    private final GymUserBusinessServiceInterface userBusinessService;
    private final GymOwnerBusinessServiceInterface ownerBusinessService;

    public GymOwnerResource() {
        this.userBusinessService = new GymUserBusinessService();
        this.ownerBusinessService = new GymOwnerBusinessService();
    }

    /**
     * API endpoint to register a new gym owner.
     * Triggered by a POST request to /owner/register.
     *
     * @param owner The GymOwner object from the incoming JSON body.
     * @return A Response with a status code.
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerOwner(GymOwner owner) {
        try {
            // 1. Create the base GymUser
            GymUser newUser = userBusinessService.createUserBean(
                    owner.getName(),
                    owner.getPassword(),
                    1, // Role for Owner
                    owner.getUserName()
            );
            userBusinessService.addUser(newUser);

            // 2. Create the specific GymOwner details
            owner.setUserId(newUser.getUserId());
            ownerBusinessService.registerOwner(owner);

            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"Gym Owner registered successfully!\"}")
                    .build();

        } catch (UsernameAlreadyExistsException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred during owner registration.\"}")
                    .build();
        }
    }

    @POST
    @Path("/gyms")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerGymCenter(GymCenter gymCenter) {
        try {
            // The business service handles creating the bean with a "Pending" status.
            ownerBusinessService.registerGymCenter(gymCenter);

            // Return 201 Created on success, along with a helpful message.
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"New gym center registered successfully. It is now pending admin approval.\"}")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred while registering the gym center.\"}")
                    .build();
        }
    }
    @POST
    @Path("/gyms/{gymId}/slots")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSlot(@PathParam("gymId") int gymId, Slot slot) {
        try {
            // The business service will create the full slot bean.
            // We just need to ensure the centerId from the URL is set correctly.
            Slot newSlot = ownerBusinessService.createSlotBean(
                    slot.getStartTime(),
                    slot.getDate(),
                    gymId // Use the ID from the URL path
            );

            ownerBusinessService.registerGymSlot(newSlot);

            // Return 201 Created on success.
            return Response.status(Response.Status.CREATED)
                    .entity("{\"message\": \"New slot added successfully to Gym Center " + gymId + ".\"}")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"An unexpected error occurred while adding the slot.\"}")
                    .build();
        }
    }

}