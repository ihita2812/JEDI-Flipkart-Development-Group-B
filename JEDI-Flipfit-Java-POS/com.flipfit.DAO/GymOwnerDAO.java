package com.flipfit.dao;

import com.flipfit.bean.*;
import java.util.List;

public interface GymOwnerDAO {
    void addOwner(GymOwner owner);
    void addGymCenter(GymCenter gymCenter);
    GymOwner getOwnerById(int ownerId);
    List<GymOwner> getAllOwners();
    List<GymCenter> getAllCenters(int ownerId);
    void updateOwner(GymOwner owner);
    void removeOwner(int ownerId);
}