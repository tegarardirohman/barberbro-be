package com.enigmacamp.barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigmacamp.barbershop.model.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, String> {
}