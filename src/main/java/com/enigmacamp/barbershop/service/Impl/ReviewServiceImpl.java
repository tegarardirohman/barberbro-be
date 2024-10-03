package com.enigmacamp.barbershop.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.Review;
import com.enigmacamp.barbershop.repository.ReviewRepository;
import com.enigmacamp.barbershop.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Review create(Review review) {
        try {
            review.setCreatedAt(System.currentTimeMillis());
            return reviewRepository.save(review);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Review> getByBarber(Barbers barber) {
        return reviewRepository.findByBarbershopId(barber);
    }

    @Override
    public List<Review> getByCustomer(Customer customer) {
        return reviewRepository.findByCustomerId(customer);
    }
}