package com.enigmacamp.barbershop.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminRegisterRequest {
    private String email;
    private String password;
}