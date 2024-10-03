package com.enigmacamp.barbershop.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingAvailableResponse {
    @JsonProperty("all_time")
    private List<String> allTime;

    @JsonProperty("available_time")
    private List<String> availableTime;
}