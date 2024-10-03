package com.enigmacamp.barbershop.service;

import java.util.List;

import com.enigmacamp.barbershop.model.dto.request.MidtransWebhookRequest;
import com.enigmacamp.barbershop.model.dto.response.BookingAvailableResponse;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Booking;
import com.enigmacamp.barbershop.model.entity.Customer;

public interface BookingService {
    Booking create(Booking booking);

    Booking getById(String bookingId);

    Booking update(Booking booking);

    List<Booking> getByCustomer(Customer customer);

    List<Booking> getByBarber(Barbers barber);

    Booking cancel(Barbers barber, String bookingId);

    Booking completed(Barbers barber, String bookingId);

    List<Booking> getAll();

    Booking getMidtransUrl(Booking booking);

    Booking updateBookingStatus(Booking booking);

    List<Booking> getAllByBarberAndDate(Barbers barber, Long date);

    Boolean bookingWebhook(MidtransWebhookRequest request);

    BookingAvailableResponse getAvailable(Barbers barber, Long date);
}