package com.enigmacamp.barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.Users;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Customer findByUserId(Users userId);
}