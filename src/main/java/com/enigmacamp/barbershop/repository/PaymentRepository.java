package com.enigmacamp.barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigmacamp.barbershop.model.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}