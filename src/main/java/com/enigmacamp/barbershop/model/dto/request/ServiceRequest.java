package com.enigmacamp.barbershop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ServiceRequest {
    @JsonProperty("service_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String serviceId;
    private String service_name;
    private Double price;
}