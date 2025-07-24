package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.*;

public interface GymUserDAO {
    void addUser(GymUser user);

    GymUser getUserById(int userId);

    GymUser getUserByUsername(String username);

    List<GymUser> getAllUsers();

    void removeUser(int userId);

    Role getRole(int role);

    // boolean validateLogin(String username, String password);
}