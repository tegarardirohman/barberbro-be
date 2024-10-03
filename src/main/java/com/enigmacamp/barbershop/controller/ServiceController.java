package com.enigmacamp.barbershop.controller;

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

import com.enigmacamp.barbershop.model.dto.request.ServiceRequest;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.dto.response.ServiceResponse;
import com.enigmacamp.barbershop.model.entity.Service;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.service.ServiceService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/barbers/services")
public class ServiceController {
    private final ServiceService serviceService;
    private final JwtHelpers jwtHelpers;

    @PostMapping("/current")
    public ResponseEntity<CommonResponse<ServiceResponse>> createService(@RequestBody ServiceRequest request,
            HttpServletRequest srvrequest) {

        Users user = jwtHelpers.getUser(srvrequest);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (user.getBarbers() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbershop not found");
        }

        Service service = Service.builder()
                .service_name(request.getService_name())
                .price(request.getPrice())
                .barbershop_id(user.getBarbers())
                .build();

        service = serviceService.create(service);

        if (service == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create service");
        }

        return ResponseEntity.ok(CommonResponse.<ServiceResponse>builder()
                .statusCode(200)
                .message("Service created successfully")
                .data(service.toResponse())
                .build());
    }

    @PutMapping("/current")
    public ResponseEntity<CommonResponse<ServiceResponse>> updateService(@RequestBody ServiceRequest request,
            HttpServletRequest srvrequest) {

        Users user = jwtHelpers.getUser(srvrequest);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (user.getBarbers() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbershop not found");
        }

        Service service = serviceService.getById(request.getServiceId());

        if (service == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found");
        }

        if (request.getService_name() != null)
            service.setService_name(request.getService_name());
        if (request.getPrice() != null)
            service.setPrice(request.getPrice());

        service = serviceService.update(service);

        return ResponseEntity.ok(CommonResponse.<ServiceResponse>builder()
                .statusCode(200)
                .message("Service updated successfully")
                .data(service.toResponse())
                .build());

    }

    @DeleteMapping("/current/{serviceId}")
    public ResponseEntity<CommonResponse<String>> deleteService(HttpServletRequest srvrequest, @PathVariable String serviceId) {
        if (serviceId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Service ID cannot be null");
        }
        
        Users user = jwtHelpers.getUser(srvrequest);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        if (user.getBarbers() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbershop not found");
        }

        Service service = serviceService.getById(serviceId);
        if (service == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found");
        }
        
        if (!service.getBarbershop_id().equals(user.getBarbers())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this service");
        }


        if (serviceService.delete(serviceId)) {
            return ResponseEntity.ok(CommonResponse.<String>builder()
                    .statusCode(200)
                    .message("Service deleted successfully")
                    .data("Service deleted successfully")
                    .build());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to delete service");
        }

    }
}