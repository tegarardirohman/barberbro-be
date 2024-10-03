package com.enigmacamp.barbershop.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BarberRegisterResponse {
    private String email;
    private String role;
}