package com.flipfit.client;
import com.flipfit.business.*;

public class GymCenterClient {
    public static void main(String[] args){
        GymCenterBusiness gymCenterBusiness = new GymCenterBusiness();
        gymCenterBusiness.getSlots();
    }
}