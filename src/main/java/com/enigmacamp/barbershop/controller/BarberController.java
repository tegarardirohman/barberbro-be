package com.enigmacamp.barbershop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.dto.request.BarberRequest;
import com.enigmacamp.barbershop.model.dto.response.BarberResponse;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.service.BarberService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BarberController {
    private final BarberService barberService;
    private final JwtHelpers jwtHelpers;

    @GetMapping("/barbers")
    public ResponseEntity<CommonResponse<List<BarberResponse>>> getBarbers() {
        try {
            List<BarberResponse> barbers = barberService.getAll();

            return ResponseEntity.ok(CommonResponse.<List<BarberResponse>>builder()
                    .statusCode(200)
                    .message("Barbers fetched successfully")
                    .data(barbers)
                    .build());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @GetMapping("/barbers/{id}")
    public ResponseEntity<CommonResponse<BarberResponse>> getBarberById(@PathVariable String id) {
        try {
            BarberResponse barbers = barberService.getById(id);
            if (barbers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
            }
            return ResponseEntity.ok(CommonResponse.<BarberResponse>builder()
                    .statusCode(200)
                    .message("Barber fetched successfully")
                    .data(barbers)
                    .build());
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/barbers/nearby")
    public ResponseEntity<CommonResponse<List<BarberResponse>>> getBarbersNearBy(@RequestParam double latitude, @RequestParam double longitude) {
        try {
            List<BarberResponse> barbers = barberService.getByNearBy(latitude, longitude);

            return ResponseEntity.ok(CommonResponse.<List<BarberResponse>>builder()
                    .statusCode(200)
                    .message("Barbers fetched successfully")
                    .data(barbers)
                    .build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/barbers/current")
    public ResponseEntity<CommonResponse<BarberResponse>> getCurrentBarber(HttpServletRequest srvrequest) {
        try {
            Users user = jwtHelpers.getUser(srvrequest);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }

            BarberResponse barbers = barberService.getCurrentBarber(user);

            if (barbers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
            }

            Barbers barbersEntity = barberService.getBarberById(barbers.getId());

            barbers.setBalance(barbersEntity.getBalance());

            return ResponseEntity.ok(CommonResponse.<BarberResponse>builder()
                    .statusCode(200)
                    .message("Barber fetched successfully")
                    .data(barbers)
                    .build());
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/barbers/current")
    public ResponseEntity<CommonResponse<BarberResponse>> updateBarber(HttpServletRequest srvrequest,
            @RequestBody BarberRequest request) {
        try {
            Users user = jwtHelpers.getUser(srvrequest);
            if (user == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }

            BarberResponse barbersResponse = barberService.getCurrentBarber(user);

            if (barbersResponse == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
            }

            Barbers barbers = barberService.getBarberById(barbersResponse.getId());

            if (barbers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
            }

            if (barbers.getName() != null)
                barbers.setName(request.getName());
            if (barbers.getContact_number() != null)
                barbers.setContact_number(request.getContact_number());
            // if (barbers.getEmail() != null) barbers.setEmail(request.getEmail());
            if (barbers.getCity() != null)
                barbers.setCity(request.getCity());
            if (barbers.getStreet_address() != null)
                barbers.setStreet_address(request.getStreet_address());
            if (barbers.getState_province_region() != null)
                barbers.setState_province_region(request.getState_province_region());
            if (barbers.getPostal_zip_code() != null)
                barbers.setPostal_zip_code(request.getPostal_zip_code());
            if (barbers.getCountry() != null)
                barbers.setCountry(request.getCountry());
            if (barbers.getLatitude() != null)
                barbers.setLatitude(request.getLatitude());
            if (barbers.getLongitude() != null)
                barbers.setLongitude(request.getLongitude());
            if (barbers.getDescription() != null)
                barbers.setDescription(request.getDescription());

            BarberResponse barbersResponseUpdate = barberService.update(barbers);

            return ResponseEntity.ok(CommonResponse.<BarberResponse>builder()
                    .statusCode(200)
                    .message("Barber updated successfully")
                    .data(barbersResponseUpdate)
                    .build());
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/barbers/{id}")
    public ResponseEntity<CommonResponse<String>> deleteBarber(@PathVariable String id) {
        try {
            BarberResponse barbers = barberService.getById(id);
            if (barbers == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barber not found");
            }

            barberService.delete(barbers.getId());

            return ResponseEntity.ok(CommonResponse.<String>builder()
                    .statusCode(200)
                    .message("Barber deleted successfully")
                    .build());
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}