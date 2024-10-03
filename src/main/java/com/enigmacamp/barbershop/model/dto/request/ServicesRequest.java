package com.enigmacamp.barbershop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ServicesRequest {
    @JsonProperty("service_name")
    private String service_name;
    @JsonProperty("price")
    private Double price;
}