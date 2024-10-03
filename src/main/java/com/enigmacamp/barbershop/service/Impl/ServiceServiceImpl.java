package com.enigmacamp.barbershop.service.Impl;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.entity.Service;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.ServiceRepository;
import com.enigmacamp.barbershop.service.ServiceService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {
    private final JwtHelpers jwtHelpers;
    private final ServiceRepository serviceRepository;

    @Override
    public Service create(HttpServletRequest srvrequest, Service service) {
        try {
            Users user = jwtHelpers.getUser(srvrequest);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }

            return serviceRepository.save(service);
        } catch (ResponseStatusException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Service getById(String id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Service create(Service service) {
        try {
            return serviceRepository.save(service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Service update(Service service) {
        try {
            if (service.getService_id() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID cannot be null");
            }

            if (service.getBarbershop_id() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Barbershop ID cannot be null");
            }

            if (serviceRepository.findById(service.getService_id()).isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found");
            }

            return serviceRepository.save(service);
        } catch (ResponseStatusException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String id) {
        try {
            serviceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}