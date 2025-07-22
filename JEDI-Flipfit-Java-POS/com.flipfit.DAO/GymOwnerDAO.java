package com.flipfit.dao;

import com.flipfit.bean.GymOwner;
import java.util.List;

public interface GymOwnerDAO {
    void addOwner(GymOwner owner);
    GymOwner getOwnerById(int ownerId);
    List<GymOwner> getAllOwners();
    void updateOwner(GymOwner owner);
    void removeOwner(int ownerId);
}