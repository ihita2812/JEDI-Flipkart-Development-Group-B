package com.flipfit.business;

import com.flipfit.bean.*;
import com.flipfit.dao.GymUserDAO;
import com.flipfit.dao.GymUserDAOImpl;

public class GymUserBusinessService implements GymUserBusinessServiceInterface {

    private GymUserDAO userDAO = new GymUserDAOImpl();

    public boolean loginUser(GymUser user) {
        GymUser foundUser = userDAO.getUserByUsername(user.getUserName());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            System.out.println("Login successful! Welcome " + foundUser.getName());
            return true;
        }
        System.out.println("Invalid username or password.");
        return false;
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