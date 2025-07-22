package com.flipflit.client;

import com.flipfit.business.*;

public class GymAdminClient {
    public static void main(String[] args ) {
        GymAdminBusiness gymAdminClient = new GymAdminBusiness();
        gymAdminClient.createAdmin(null);
        gymAdminClient.viewRegisteredGyms();
        gymAdminClient.approveGym(null);
        gymAdminClient.removeGym(null);
        gymAdminClient.viewPaymentsInformation();
    }
}