package com.enigmacamp.barbershop.seeders;

import org.springframework.stereotype.Component;

import com.enigmacamp.barbershop.model.dto.request.AdminRegisterRequest;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.service.AuthService;

import jakarta.annotation.PostConstruct;

@Component
public class AdminSeeder {

    private final AuthService authService;

    public AdminSeeder(AuthService authService) {
        this.authService = authService;
    }

    @PostConstruct
    public void seed() {
        Users admin = authService.getUserByEmail("barberbo.admin@mail.com");

        if (admin == null) {
            AdminRegisterRequest adminRegisterRequest = AdminRegisterRequest.builder()
                    .email("barberbo.admin@mail.com")
                    .password("barberbo.admin@mail.com")
                    .build();

            authService.registerAdmin(adminRegisterRequest);
        }

    }
}