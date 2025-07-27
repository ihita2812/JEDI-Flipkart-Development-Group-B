package com.flipfit.rest;

import com.flipfit.bean.GymUser;
import com.flipfit.business.GymUserBusinessService;
import com.flipfit.business.GymUserBusinessServiceInterface;
import com.flipfit.exception.InvalidCredentialsException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    private final GymUserBusinessServiceInterface userBusinessService;

    public AuthenticationResource() {
        this.userBusinessService = new GymUserBusinessService();
    }

    /**
     * API endpoint for user login.
     * Triggered by a POST request to /login.
     *
     * @param credentials A GymUser object from the JSON, expected to contain userName, password, and role.
     * @return A Response with a success or failure message.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(GymUser credentials) {
        try {
            // The business service throws an exception if login fails.
            int roleSpecificId = userBusinessService.loginUser(
                    credentials.getUserName(),
                    credentials.getPassword(),
                    credentials.getRole().getRoleId()
            );

            // If login succeeds, return a 200 OK with the user's role-specific ID.
            // A real-world app would return a secure token (JWT), but this is great for our project.
            return Response.ok(Map.of(
                    "message", "Login successful!",
                    "userId", roleSpecificId,
                    "role", credentials.getRole().getRoleId()
            )).build();

        } catch (InvalidCredentialsException e) {
            // If login fails, return 401 Unauthorized.
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("message", e.getMessage()))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("message", "An unexpected error occurred during login."))
                    .build();
        }
    }
    @PUT
    @Path("/{username}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(@PathParam("username") String username, Map<String, String> passwords) {
        try {
            String oldPassword = passwords.get("oldPassword");
            String newPassword = passwords.get("newPassword");

            // Basic validation to ensure both passwords are provided.
            if (oldPassword == null || newPassword == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(Map.of("message", "Request body must include 'oldPassword' and 'newPassword'."))
                        .build();
            }

            boolean isChanged = userBusinessService.changePassword(username, oldPassword, newPassword);

            if (isChanged) {
                return Response.ok(Map.of("message", "Password changed successfully for user: " + username)).build();
            } else {
                // The business logic returns false if the username doesn't exist or the old password is incorrect.
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("message", "Failed to change password. Please check your username and old password."))
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("message", "An unexpected error occurred."))
                    .build();
        }
    }
}