package com.enigmacamp.barbershop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.model.dto.request.CustomerRequest;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.dto.response.CustomerResponse;
import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.service.CustomerService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;
    private final JwtHelpers jwtHelpers;

    @GetMapping("/customers")
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getCustomers() {
        List<Customer> customers = customerService.getCustomers();

        if (customers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customers not found");
        }

        return ResponseEntity.ok(CommonResponse.<List<CustomerResponse>>builder()
                .statusCode(200)
                .message("Customers found")
                .data(customers.stream().map(customer -> customer.toResponse()).toList())
                .build());
    }

    @GetMapping("/customers/current")
    public ResponseEntity<CommonResponse<CustomerResponse>> getCurrentCustomer(HttpServletRequest srvrequest) {

        Users user = jwtHelpers.getUser(srvrequest);
        Customer customer = customerService.getByUserId(user);

        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        return ResponseEntity.ok(CommonResponse.<CustomerResponse>builder()
                .statusCode(200)
                .message("Customer found")
                .data(customer.toResponse())
                .build());
    }

    @PutMapping("/customers/current")
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(HttpServletRequest srvrequest,
            @RequestBody CustomerRequest request) {

        Users user = jwtHelpers.getUser(srvrequest);
        Customer customer = customerService.getByUserId(user);

        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        if (request.getFirstName() != null) customer.setFirstName(request.getFirstName());
        if (request.getSurname() != null) customer.setSurname(request.getSurname());
        if (request.getIsMale() != null) customer.setIsMale(request.getIsMale());
        if (request.getDateOfBirth() != null) customer.setDateOfBirth(request.getDateOfBirth());
        // if (request.getEmail() != null) customer.setEmail(request.getEmail());
        if (request.getPhone() != null) customer.setPhone(request.getPhone());
        if (request.getAddress() != null) customer.setAddress(request.getAddress());
        if (request.getAbout() != null) customer.setAbout(request.getAbout());

        customer = customerService.create(customer, srvrequest);

        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update customer");
        }

        return ResponseEntity.ok(CommonResponse.<CustomerResponse>builder()
                .statusCode(200)
                .message("Customer updated successfully")
                .data(customer.toResponse())
                .build());
    }
}