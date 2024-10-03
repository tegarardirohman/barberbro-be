package com.enigmacamp.barbershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, String>{

    List<Review> findByBarbershopId(Barbers barber);

    List<Review> findByCustomerId(Customer customer);
}