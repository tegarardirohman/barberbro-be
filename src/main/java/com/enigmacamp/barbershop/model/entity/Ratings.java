package com.enigmacamp.barbershop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_ratings")
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking bookingId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "review", nullable = false)
    private String amount;

    @Column(name = "created_at", nullable = false,updatable = false)
    private Long createdAt;

    @Column(name = "updated_at", nullable = false)
    private Long updatedAt;
}
