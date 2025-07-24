package com.flipfit.DAO;

import com.flipfit.bean.*;
import com.flipfit.dao.GymAdminDAO;
import java.util.*;
import com.flipfit.dao.GymUserDAOImpl;

public class GymAdminDAOImpl implements GymAdminDAO {

    public static Map<Integer, GymAdmin> adminMap = new HashMap<>();

    public GymAdminDAOImpl() {

        Role adminRole = GymUserDAOImpl.roleMap.get(2);

        GymAdmin admin1 = new GymAdmin();
        admin1.setUserId(1);
        admin1.setUserName("admin");
        admin1.setPassword("admin");
        admin1.setName("Admin User");
        admin1.setRole(adminRole);
        admin1.setAdminId(1);
        adminMap.put(admin1.getAdminId(), admin1);

        GymAdmin admin2 = new GymAdmin();
        admin2.setUserId(4);
        admin2.setUserName("admin1");
        admin2.setPassword("admin");
        admin2.setName("Admin User hu mai");
        admin2.setRole(adminRole);
        admin2.setAdminId(2);
        adminMap.put(admin2.getAdminId(), admin2);
    }


    /*

    @Override
    public void addAdmin(GymAdmin admin) {
        for (GymAdmin existing : adminMap.values()) {
            if (existing.getUserName().equals(admin.getUserName())) {
                System.out.println("Username already exists!");
                return;
            }
        }

        int newAdminId = adminMap.keySet().stream()
                                 .max(Integer::compareTo)
                                 .orElse(0) + 1;
        admin.setAdminId(newAdminId);
        admin.setUserId(newAdminId);
        adminMap.put(newAdminId, admin);
        System.out.println("Admin added successfully with ID: " + newAdminId);
    }

    @Override
    public GymAdmin getAdminById(int adminId) {
        return adminMap.get(adminId);
    }

    @Override
    public List<GymAdmin> getAllAdmins() {
        return new ArrayList<>(adminMap.values());
    }

    @Override
    public void updateAdmin(GymAdmin admin) {
        adminMap.put(admin.getAdminId(), admin);
    }

    @Override
    public void removeAdmin(int adminId) {
        adminMap.remove(adminId);
    }
    */
}