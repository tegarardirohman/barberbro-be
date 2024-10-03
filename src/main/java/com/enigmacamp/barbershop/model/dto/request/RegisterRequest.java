package com.enigmacamp.barbershop.model.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String role;
}
