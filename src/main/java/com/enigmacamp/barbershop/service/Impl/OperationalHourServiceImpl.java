package com.enigmacamp.barbershop.service.Impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.entity.OperationalHour;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.repository.OperationalHourRepository;
import com.enigmacamp.barbershop.service.OperationalHourService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OperationalHourServiceImpl implements OperationalHourService {
    private final OperationalHourRepository operationalHourRepository;
    private final JwtHelpers jwtHelpers;

    @Override
    public OperationalHour create(HttpServletRequest srvrequest, OperationalHour request) {
        try {
            Users user = jwtHelpers.getUser(srvrequest);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }

            return operationalHourRepository.save(request);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OperationalHour update(HttpServletRequest srvrequest, OperationalHour request) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OperationalHour update(OperationalHour request) {
        try {
            return operationalHourRepository.save(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OperationalHour> getAll() {
        return null;
    }

    @Override
    public OperationalHour getById(String id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String id) {
        try {
            operationalHourRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}