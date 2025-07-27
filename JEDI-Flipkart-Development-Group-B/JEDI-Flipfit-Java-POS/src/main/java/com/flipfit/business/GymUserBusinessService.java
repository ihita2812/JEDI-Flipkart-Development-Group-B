package com.flipfit.business;

import com.flipfit.bean.GymUser;
import com.flipfit.bean.Notification;
import com.flipfit.bean.Role;
import com.flipfit.dao.GymUserDAO;
import com.flipfit.dao.GymUserDAOImpl;
import com.flipfit.exception.InvalidCredentialsException;
import com.flipfit.exception.UsernameAlreadyExistsException;


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

        // Check for invalid user or wrong password
        if (foundUser == null || !foundUser.getPassword().equals(password)) {
            throw new InvalidCredentialsException();
        }

        System.out.printf("%n****************** LOGGED USER ******************%n");
        Role foundRole = foundUser.getRole();

        // Check for role mismatch
        if (foundRole.getRoleId() != role) {
            throw new InvalidCredentialsException();
        }

        // Successful login path
        return switch (foundRole.getRoleId()) {
            case 0 -> userDAO.getCustomerId(foundUser);
            case 1 -> userDAO.getOwnerId(foundUser);
            case 2 -> userDAO.getAdminId(foundUser);
            default -> throw new InvalidCredentialsException(); // Should be unreachable
        };
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
        // This business logic now belongs here, not in the client.
        if (userDAO.getUserByUsername(user.getUserName()) != null) {
            throw new UsernameAlreadyExistsException(user.getUserName());
        }
        userDAO.addUser(user);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        GymUser user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            userDAO.updateUserPassword(username, newPassword);
            return true;
        }

        return false;
    }

}