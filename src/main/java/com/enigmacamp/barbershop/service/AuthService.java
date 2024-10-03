package com.enigmacamp.barbershop.service;

import com.enigmacamp.barbershop.model.dto.request.AdminRegisterRequest;
import com.enigmacamp.barbershop.model.dto.request.BarberRegisterRequest;
import com.enigmacamp.barbershop.model.dto.request.LoginRequest;
import com.enigmacamp.barbershop.model.dto.request.RegisterRequest;
import com.enigmacamp.barbershop.model.dto.response.BarberRegisterResponse;
import com.enigmacamp.barbershop.model.dto.response.LoginResponse;
import com.enigmacamp.barbershop.model.dto.response.RegisterResponse;
import com.enigmacamp.barbershop.model.entity.Users;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    RegisterResponse registerCustomer(RegisterRequest request, HttpServletRequest srvrequest);

    BarberRegisterResponse registerBarber(BarberRegisterRequest request, HttpServletRequest srvrequest);

    RegisterResponse registerAdmin(AdminRegisterRequest request);

    LoginResponse login(LoginRequest request);

    Users getUserByEmail(String email);
}
