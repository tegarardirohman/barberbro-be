package com.enigmacamp.barbershop.model.entity;

import com.enigmacamp.barbershop.model.dto.response.ReviewResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "barbershop_id", referencedColumnName = "id", nullable = false)
    private Barbers barbershopId;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id", nullable = false)
    private Booking bookingId;

    @Column(name = "rating", nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    public ReviewResponse toResponse() {

        return ReviewResponse.builder()
                .id(id)
                .customerId(customerId.getId())
                .customerName(customerId.getFirstName() + " " + customerId.getSurname())
                .barbershopId(barbershopId.getId())
                .barbershopName(barbershopId.getName())
                .bookingId(bookingId.getBookingId())
                .services(bookingId.getServices().stream().map(service -> service.getService_name()).toList())
                .rating(rating)
                .comment(comment)
                .createdAt(createdAt)
                .build();
    }
}