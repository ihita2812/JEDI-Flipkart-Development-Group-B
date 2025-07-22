package com.flipflit.client;

import com.flipfit.business.*;

public class GymCustomerClient {
    public static void main(String[] args ) {
        GymCustomerBusiness gymCustomerClient = new GymCustomerBusiness();
        gymCustomerClient.createGymCustomer(null);
        gymCustomerClient.viewGymCenter();
        gymCustomerClient.bookSlot(null);
        gymCustomerClient.cancelSlot();
        gymCustomerClient.makePayment(null);
    }
}
