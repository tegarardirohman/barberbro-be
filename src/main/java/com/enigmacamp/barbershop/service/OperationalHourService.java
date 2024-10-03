package com.enigmacamp.barbershop.service;

import java.util.List;

import com.enigmacamp.barbershop.model.entity.OperationalHour;

import jakarta.servlet.http.HttpServletRequest;

public interface OperationalHourService {
    OperationalHour create(HttpServletRequest srvrequest, OperationalHour request);

    OperationalHour update(HttpServletRequest srvrequest, OperationalHour request);

    OperationalHour update(OperationalHour request);

    List<OperationalHour> getAll();

    OperationalHour getById(String id);

    Boolean delete(String id);
}