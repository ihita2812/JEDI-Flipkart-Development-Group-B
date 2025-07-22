package com.flipfit.dao;

import com.flipfit.bean.GymCustomer;
import java.util.List;

public interface GymCustomerDAO {
    void addCustomer(GymCustomer customer);
    GymCustomer getCustomerById(int customerId);
    List<GymCustomer> getAllCustomers();
    void updateCustomer(GymCustomer customer);
    void removeCustomer(int customerId);
}