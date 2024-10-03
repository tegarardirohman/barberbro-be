package com.enigmacamp.barbershop.service.Impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.CustomerRepository;
import com.enigmacamp.barbershop.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer create(Customer customer, HttpServletRequest srvrequest) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Customer getByUserId(Users user) {
        return customerRepository.findByUserId(user);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(Customer customer, HttpServletRequest srvrequest) {
        try {
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}