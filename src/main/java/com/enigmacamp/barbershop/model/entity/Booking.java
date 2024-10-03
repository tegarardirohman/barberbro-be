package com.enigmacamp.barbershop.model.entity;

import java.time.LocalTime;
import java.util.List;

import com.enigmacamp.barbershop.model.dto.response.BookingResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "booking_id", nullable = false)
    private String bookingId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private Barbers barberId;

    @ManyToMany
    @JoinTable(name = "m_booking_services", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Service> services;

    @Column(name = "booking_date", nullable = false)
    private Long bookingDate;

    @Column(name = "booking_time", nullable = false)
    private LocalTime bookingTime;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_price", nullable = true)
    private Double totalPrice;

    @Column(name = "midtrans_payment_url", nullable = true)
    private String midtransPaymentUrl;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;

    public BookingResponse toResponse() {
        barberId.setServices(null);
        barberId.setOperationalHours(null);
        barberId.setSocial_media(null);
        return BookingResponse.builder()
                .booking_id(this.bookingId)
                .customer(customerId.toResponse())
                .barber(barberId.toResponse())
                .services(services.stream().map(service -> service.toResponse()).toList())
                .bookingDate(bookingDate)
                .bookingTime(bookingTime.toString())
                .status(status)
                .midtransPaymentUrl(midtransPaymentUrl)
                .totalPrice(totalPrice)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}