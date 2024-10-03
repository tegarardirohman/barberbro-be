package com.enigmacamp.barbershop.service;

import java.util.List;

import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.Users;

import jakarta.servlet.http.HttpServletRequest;

public interface CustomerService {
    Customer create(Customer customer, HttpServletRequest srvrequest);

    Customer update(Customer customer, HttpServletRequest srvrequest);

    Customer getByUserId(Users user);

    List<Customer> getCustomers();
}