package com.enigmacamp.barbershop.service;

import java.util.Map;

import com.enigmacamp.barbershop.model.entity.Booking;
import com.enigmacamp.barbershop.model.entity.Payment;

public interface PaymentService {
    Payment create(Payment payment, Booking booking);

    Boolean updatePaymentStatusWebhook(Map<String, Object> params);

    Payment updatePaymentStatus(Payment payment, Booking booking);

    Payment getById(String id);
}