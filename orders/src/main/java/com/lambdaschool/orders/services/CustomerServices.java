package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;

import java.util.List;

public interface CustomerServices {
    List<Customer> findAllCustomers();
    Customer findByCustcode(long custcode);
    List<Customer> findByNameLike(String matcher);
}
