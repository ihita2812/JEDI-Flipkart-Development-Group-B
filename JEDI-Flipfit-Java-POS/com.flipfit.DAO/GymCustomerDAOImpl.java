package com.flipfit.dao;

import com.flipfit.bean.GymCustomer;
import java.util.*;

public class GymCustomerDAOImpl implements GymCustomerDAO {

    private Map<Integer, GymCustomer> customerMap = new HashMap<>();

    @Override
    public void addCustomer(GymCustomer customer) {
        customerMap.put(customer.getCustomerId(), customer);
    }

    @Override
    public GymCustomer getCustomerById(int customerId) {
        return customerMap.get(customerId);
    }

    @Override
    public List<GymCustomer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public void updateCustomer(GymCustomer customer) {
        customerMap.put(customer.getCustomerId(), customer);
    }

    @Override
    public void removeCustomer(int customerId) {
        customerMap.remove(customerId);
    }
}