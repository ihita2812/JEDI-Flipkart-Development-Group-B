package com.flipfit.dao;


/*
 *@author:Ritesh, Ihita
 *@ClassName:GymAdminDAOImpl
 *@Exceptions:
 *@version:1.0
 *@See :GymAdminDAO
 */
public class GymAdminDAOImpl implements GymAdminDAO {

    //public static Map<Integer, GymAdmin> adminMap = new HashMap<>();

    public GymAdminDAOImpl() {

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