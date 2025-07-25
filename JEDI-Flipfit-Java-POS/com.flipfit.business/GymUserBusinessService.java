package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.GymUserDAO;
import com.flipfit.dao.GymUserDAOImpl;

import java.time.LocalDate;
import java.time.LocalTime;


/*
 *@author:Aryan, Ihita, Kashif
 *@ClassName:GymUserBusinessService
 *@version:1.0
 *@See :GymUserBusinessServiceInterface
 */
public class GymUserBusinessService implements GymUserBusinessServiceInterface {

    private GymUserDAO userDAO = new GymUserDAOImpl();

    // login user takes username and password and checks if user exists
    // if user exists, it gets the role and returns role specific id
    // otherwise returns -1
    public int loginUser(String username, String password, int role) {
        GymUser foundUser = userDAO.getUserByUsername(username);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            System.out.printf("%n****************** LOGGED USER ******************%n");
            Role foundRole = foundUser.getRole();
            if (foundRole != userDAO.getRole(role)) // check if the role matches
                return -2;
            else {
                switch (foundRole.getRoleId()) {
                    case 0: // Customer
                        return userDAO.getCustomerId(foundUser);
                    case 1: // Owner
                        return userDAO.getOwnerId(foundUser);
                    case 2: // Admin
                        return userDAO.getAdminId(foundUser);
                }
            }
        }
        return -1;
    }

    public Notification[] viewNotifications(GymUser user) {
        System.out.println("Here are you notifications!");
        return null;
    }

    public boolean userNameExists(String userName) {
        return userDAO.getUserByUsername(userName) != null;
    }

    public GymUser createUserBean(String name, String pass, int role, String userName) {
        GymUser user = new GymUser();
        user.setUserName(userName);
        user.setRole(userDAO.getRole(role));
        user.setName(name);
        user.setPassword(pass);
        // The DAO will set the user ID when the user is added to the database.
        return user;
    }

    public void addUser(GymUser user) {
        userDAO.addUser(user);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        GymUser user = userDAO.getUserByUsername(username);

        // Check if user exists and if the provided old password is correct
        if (user != null && user.getPassword().equals(oldPassword)) {
            // If both are true, call the DAO to update the password
            userDAO.updateUserPassword(username, newPassword);
            return true; // Return true indicating success
        }

        // If user does not exist or old password was incorrect, return false
        return false;
    }

}