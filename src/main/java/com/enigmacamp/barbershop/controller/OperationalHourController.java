package com.enigmacamp.barbershop.controller;

import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.dto.request.OperationalHoursRequest;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.dto.response.OperationalHourResponse;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.OperationalHour;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.service.OperationalHourService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OperationalHourController {
    private final OperationalHourService operationalHourService;
    private final JwtHelpers jwtHelpers;

    @PutMapping("/barbers/operational-hour/current")
    public ResponseEntity<CommonResponse<OperationalHourResponse>> updateCurrent(
            @RequestBody OperationalHoursRequest request, HttpServletRequest srvrequest) {
        Users user = jwtHelpers.getUser(srvrequest);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Barbers barbers = user.getBarbers();

        if (barbers == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
        }

        OperationalHour operationalHour = barbers.getOperationalHours().stream()
                .filter(hour -> request.getOperatingHourId().equals(hour.getOperating_hours_id())).findFirst()
                .orElse(null);

        if (operationalHour == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operational hour not found");
        }

        if (request.getOpening_time() != null)
            operationalHour.setOpening_time(LocalTime.parse(request.getOpening_time()));
        if (request.getClosing_time() != null)
            operationalHour.setClosing_time(LocalTime.parse(request.getClosing_time()));
        if (request.getDay() != null)
            operationalHour.setDay(request.getDay().name());
        if (request.getLimitPerSession() != null)
            operationalHour.setLimitPerSession(request.getLimitPerSession());

        operationalHour = operationalHourService.update(operationalHour);

        return ResponseEntity.ok(CommonResponse.<OperationalHourResponse>builder()
                .statusCode(200)
                .message("Operational hour updated successfully")
                .data(operationalHour.toResponse())
                .build());

    }

    @PostMapping("/barbers/operational-hour/current")
    public ResponseEntity<CommonResponse<OperationalHourResponse>> create(@RequestBody OperationalHoursRequest request,
            HttpServletRequest srvrequest) {
        Users user = jwtHelpers.getUser(srvrequest);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Barbers barbers = user.getBarbers();
        if (barbers == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
        }

        OperationalHour operationalHour = OperationalHour.builder()
                .barbershop_id(barbers)
                .day(request.getDay().name())
                .opening_time(LocalTime.parse(request.getOpening_time()))
                .closing_time(LocalTime.parse(request.getClosing_time()))
                .limitPerSession(request.getLimitPerSession())
                .build();

        operationalHour = operationalHourService.create(srvrequest, operationalHour);

        return ResponseEntity.ok(CommonResponse.<OperationalHourResponse>builder()
                .statusCode(200)
                .message("Operational hour created successfully")
                .data(operationalHour.toResponse())
                .build());
    }

    @DeleteMapping("/barbers/operational-hour/current/{id}")
    public ResponseEntity<CommonResponse<String>> delete(HttpServletRequest srvrequest, @PathVariable String id) {
        Users user = jwtHelpers.getUser(srvrequest);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (!operationalHourService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Operational hour not found");
        }

        return ResponseEntity.ok(CommonResponse.<String>builder()
                .statusCode(200)
                .message("Operational hour deleted successfully")
                .data("Operational hour deleted successfully")
                .build());
    }

}