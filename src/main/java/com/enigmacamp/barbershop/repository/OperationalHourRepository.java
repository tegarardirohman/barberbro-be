package com.enigmacamp.barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigmacamp.barbershop.model.entity.OperationalHour;

@Repository
public interface OperationalHourRepository extends JpaRepository<OperationalHour, String> {
}