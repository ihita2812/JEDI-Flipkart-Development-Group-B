package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.GymUserDAO;
import com.flipfit.dao.GymUserDAOImpl;

public class GymUserBusinessService implements GymUserBusinessServiceInterface {

    private GymUserDAO userDAO = new GymUserDAOImpl();

    // login user takes username and password and checks if user exists
    // if user exists, it gets the role and returns role specific id
    // otherwise returns -1
    public int loginUser(String username, String password, int role) {
        GymUser foundUser = userDAO.getUserByUsername(username);
        if (foundUser != null && foundUser.getPassword().equals(password)) {
            Role foundRole = foundUser.getRole();
            if (foundRole != userDAO.getRole(role)) // check if the role matches
                return -2;
            else {
                switch (foundRole.getRoleId()) {
                    case 0: // Customer
                        if (foundUser instanceof GymCustomer) {
                            return ((GymCustomer) foundUser).getCustomerId();
                        }
                        break;
                    case 1: // Owner
                        if (foundUser instanceof GymOwner) {
                            return ((GymOwner) foundUser).getOwnerId();
                        }
                        break;
                    case 2: // Admin
                        return ((GymAdmin) foundUser).getAdminId();
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

}