package com.enigmacamp.barbershop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Booking;
import com.enigmacamp.barbershop.model.entity.Customer;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    Optional<List<Booking>> findByCustomerId(Customer customer);

    Optional<List<Booking>> findByBarberId(Barbers barber);

    Optional<Booking> findByBarberIdAndBookingId(Barbers barber, String bookingId);

    // @Query("SELECT b FROM Booking b WHERE b.barber = :barber AND b.bookingDate =
    // :bookingDate")
    // List<Booking> findBookingsByBarberAndBookingDate(@Param("barber") Barbers
    // barber,
    // @Param("bookingDate") LocalDate bookingDate);

    // @Query("SELECT b FROM Booking b WHERE b.barberId = :barber AND b.bookingDate = :bookingDate")
    // List<Booking> findByBarberIdAndBookingDate(@Param("barber") Barbers barber, @Param("bookingDate") Long bookingDate);

    @Query("SELECT b FROM Booking b WHERE b.barberId = :barber AND b.bookingDate BETWEEN :startDate AND :endDate")
    List<Booking> findByBarberIdAndBookingDateRange(
            @Param("barber") Barbers barber,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate
    );
}