package com.enigmacamp.barbershop.model.entity;

import java.time.LocalTime;

import com.enigmacamp.barbershop.model.dto.response.OperationalHourResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_operational_hour")
public class OperationalHour {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String operating_hours_id;

    @ManyToOne
    @JoinColumn(name = "barbershop_id", referencedColumnName = "id", nullable = false)
    private Barbers barbershop_id;

    @Pattern(regexp = "^(SUNDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY)$", message = "day_of_week must be only SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY")
    private String day;

    @Column(name = "limit_per_session", nullable = false)
    private Integer limitPerSession;

    private LocalTime opening_time;
    private LocalTime closing_time;

    public OperationalHourResponse toResponse() {
        return OperationalHourResponse.builder()
                .operating_hours_id(operating_hours_id)
                .barbershop_id(barbershop_id.getId())
                .day(day)
                .opening_time(opening_time.toString())
                .closing_time(closing_time.toString())
                .limitPerSession(limitPerSession)
                .build();
    }
}