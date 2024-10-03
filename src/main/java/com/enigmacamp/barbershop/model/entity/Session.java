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
@Table(name = "m_sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private Barbers barberId;

    @Column(name = "start_time",nullable = false)
    private Long startTime;

    @Column(name = "end_time",nullable = false)
    private Long endTime;

    @Column(name = "free_time",nullable = false)
    private Long freeTime;

    @Column(name = "time_per_session",nullable = false)
    private Integer timePerSession;

    @Column(name = "days",nullable = false)
    private String days;

    @Column(name = "available_barbers",nullable = false)
    private Integer availableBarbers;

    @Column(name = "created_at",nullable = false,updatable = false)
    private Long createdAt;

    @Column(name = "updated_at",nullable = false)
    private Long updatedAt;
}
