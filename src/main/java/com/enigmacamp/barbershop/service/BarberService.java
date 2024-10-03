package com.enigmacamp.barbershop.service;

import java.util.List;

import com.enigmacamp.barbershop.model.dto.request.BarberRequest;
import com.enigmacamp.barbershop.model.dto.response.BarberResponse;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Users;

import jakarta.servlet.http.HttpServletRequest;

public interface BarberService {
    BarberResponse create(HttpServletRequest srvrequest, BarberRequest request);

    Barbers update(HttpServletRequest srvrequest, BarberRequest request);

    BarberResponse update(Barbers barbers);

    Barbers getByEmail(String email);

    List<BarberResponse> getAll();

    BarberResponse getById(String id);

    Barbers getBarberById(String id);

    Barbers getByUserId(Users user);

    List<BarberResponse> getByNearBy(double latitude, double longitude);

    BarberResponse getCurrentBarber(Users user);

    Boolean delete(String id);
}
