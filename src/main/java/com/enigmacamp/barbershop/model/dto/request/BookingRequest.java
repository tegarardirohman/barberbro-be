package com.enigmacamp.barbershop.model.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BookingRequest {
    private String barber_id;

    private List<String> services;

    @JsonProperty("booking_date")
    private Long bookingDate;

    @JsonProperty("booking_time")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Invalid time format, must be in HH:mm format")
    private String bookingTime;
}