package com.flipfit.client;

import com.flipfit.business.*;

public class GymOwnerClient {
    public static void main(String[] args){
        GymOwnerBusiness gymOwnerBusiness = new GymOwnerBusiness();
        gymOwnerBusiness.viewGymCenters();
        gymOwnerBusiness.viewBookingDetails(null);
        gymOwnerBusiness.addSlotsAndCapacity(null,0, 0);
        gymOwnerBusiness.viewNotifications();
    }
}
