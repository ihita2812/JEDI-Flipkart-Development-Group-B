package com.flipfit.rest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class FlipfitApplication extends Application<FlipfitConfiguration> {

    public static void main(String[] args) throws Exception {
        // This is the "start button" for our server.
        new FlipfitApplication().run(args);
    }

    @Override
    public String getName() {
        return "Flipfit-API";
    }

    @Override
    public void initialize(Bootstrap<FlipfitConfiguration> bootstrap) {
        // We will leave this empty for now.
    }

    @Override
    public void run(FlipfitConfiguration configuration, Environment environment) {
        // Create instances of all our Resource classes.
        final GymAdminResource adminResource = new GymAdminResource();
        final GymCustomerResource customerResource = new GymCustomerResource();
        final GymOwnerResource ownerResource = new GymOwnerResource();
        final AuthenticationResource authResource = new AuthenticationResource(); // <-- NEW

        // Register all resources.
        environment.jersey().register(adminResource);
        environment.jersey().register(customerResource);
        environment.jersey().register(ownerResource);
        environment.jersey().register(authResource); // <-- NEW

        System.out.println("----------------------------------------------------------");
        System.out.println("Flipfit Dropwizard Application has started successfully!");
        System.out.println("Registered Resources: /admin, /customer, /owner, /login"); // <-- UPDATED
        System.out.println("Access the API at http://localhost:8080");
        System.out.println("----------------------------------------------------------");
    }
}