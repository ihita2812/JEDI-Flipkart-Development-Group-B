package com.flipfit.client;

import com.flipfit.business.GymUserBusiness;

public class GymUserClient {
    public static void main(String[] args)
    {
        GymUserBusiness userClient = new GymUserBusiness();
        userClient.createUser(null);
        userClient.loginUser(null);
    }

}
