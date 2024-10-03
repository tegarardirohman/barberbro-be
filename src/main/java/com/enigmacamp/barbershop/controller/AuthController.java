package com.enigmacamp.barbershop.controller;

import com.enigmacamp.barbershop.model.dto.request.LoginRequest;
import com.enigmacamp.barbershop.model.dto.request.RegisterRequest;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.dto.response.LoginResponse;
import com.enigmacamp.barbershop.model.dto.response.RegisterResponse;
import com.enigmacamp.barbershop.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigmacamp.barbershop.constant.UserRole;
import com.enigmacamp.barbershop.model.dto.request.BarberRegisterRequest;
import com.enigmacamp.barbershop.model.dto.response.BarberRegisterResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    @PostMapping("/customer/register")
    public ResponseEntity<CommonResponse<RegisterResponse>> registerCustomer(@RequestBody RegisterRequest request,
            HttpServletRequest srvrequest) {
        request.setRole(UserRole.CUSTOMER.name());
        logger.info("Accessed Endpoint : " + "/register");

        RegisterResponse response = authService.registerCustomer(request, srvrequest);
        return ResponseEntity.ok(CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully registered new account")
                .data(response)
                .build());
    }

    @PostMapping("/barber/register")
    public ResponseEntity<CommonResponse<BarberRegisterResponse>> registerBarber(
            @RequestBody BarberRegisterRequest request,
            HttpServletRequest srvrequest) {

        BarberRegisterResponse response = authService.registerBarber(request, srvrequest);

        if (response == null) {
            return null;
        }

        return ResponseEntity.ok(CommonResponse.<BarberRegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Successfully registered new barber account")
                .data(response)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        logger.info("Accessed Endpoint : " + "/login");

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Login successful")
                .data(response)
                .build());
    }
}
