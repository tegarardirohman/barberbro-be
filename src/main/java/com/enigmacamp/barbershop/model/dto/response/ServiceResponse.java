package com.enigmacamp.barbershop.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {
    private String service_id;
    private String service_name;
    private Double price;
}