package com.enigmacamp.barbershop.service;

import java.util.List;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.Review;

public interface ReviewService {
    Review create(Review review);

    List<Review> getByBarber(Barbers barber);

    List<Review> getByCustomer(Customer customer);
}