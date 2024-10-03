package com.enigmacamp.barbershop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReviewRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonProperty("barbershop_id")
    private String barbershopId;

    @JsonProperty("booking_id")
    private String bookingId;

    @JsonProperty("rating")
    private Integer rating;

    private String comment;
}