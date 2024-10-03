package com.enigmacamp.barbershop.model.dto.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponse {
    private String id;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("barbershop_id")
    private String barbershopId;

    @JsonProperty("barbershop_name")
    private String barbershopName;

    @JsonProperty("services")
    private List<String> services;

    @JsonProperty("booking_id")
    private String bookingId;

    @JsonProperty("rating")
    private Integer rating;

    private String comment;

    private Long createdAt;
}