package com.enigmacamp.barbershop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.constant.WithdrawalStatus;
import com.enigmacamp.barbershop.model.dto.request.WithdrawalRequest;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.dto.response.WithdrawalResponse;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Users;
import com.enigmacamp.barbershop.model.entity.Withdrawals;
import com.enigmacamp.barbershop.service.WithdrawalService;
import com.enigmacamp.barbershop.util.JwtHelpers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/withdrawals")
public class WithdrawalController {
    private final WithdrawalService withdrawalService;
    private final JwtHelpers jwtHelpers;

    @GetMapping
    public ResponseEntity<CommonResponse<List<WithdrawalResponse>>> getAllWithdrawals() {
        List<Withdrawals> withdrawals = withdrawalService.getAll();

        if (withdrawals.isEmpty()) {
            return ResponseEntity.ok(CommonResponse.<List<WithdrawalResponse>>builder()
                    .statusCode(200)
                    .message("Social media created successfully")
                    .data(new ArrayList<>())
                    .build());
        }

        List<WithdrawalResponse> withdrawalResponses = withdrawals.stream()
                .map(w -> w.toResponse())
                .toList();

        return ResponseEntity.ok(CommonResponse.<List<WithdrawalResponse>>builder()
                .statusCode(200)
                .message("Social media created successfully")
                .data(withdrawalResponses)
                .build());
    }

    @PostMapping
    public ResponseEntity<CommonResponse<WithdrawalResponse>> createWithdrawal(
            @RequestBody WithdrawalRequest withdrawalRequest, HttpServletRequest srvrequest) {

        Users user = jwtHelpers.getUser(srvrequest);

        Barbers barber = user.getBarbers();

        if (barber == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Barber not found");
        }

        if (withdrawalRequest.getWithdrawalAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Withdrawal amount must be greater than 0");
        }

        if (withdrawalRequest.getWithdrawalAmount() > barber.getBalance()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Withdrawal amount cannot be greater than balance");
        }

        Withdrawals withdrawal = Withdrawals.builder()
                .barberId(barber)
                .accountNumber(withdrawalRequest.getAccountNumber())
                .accountName(withdrawalRequest.getAccountName())
                .bankName(withdrawalRequest.getBankName())
                .withdrawalAmount(withdrawalRequest.getWithdrawalAmount())
                .status(WithdrawalStatus.PENDING.name())
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .build();

        withdrawal = withdrawalService.create(withdrawal);

        if (withdrawal == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to create withdrawal");
        }

        return ResponseEntity.ok(CommonResponse.<WithdrawalResponse>builder()
                .statusCode(201)
                .message("Withdrawal created successfully")
                .data(withdrawal.toResponse())
                .build());
    }

    @GetMapping("/current")
    public ResponseEntity<CommonResponse<List<WithdrawalResponse>>> getCurrentWithdrawal(
            HttpServletRequest srvrequest) {
        Users user = jwtHelpers.getUser(srvrequest);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Barbers barber = user.getBarbers();

        if (barber == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Barber not found");
        }

        List<Withdrawals> withdrawals = withdrawalService.getAllByBarber(barber);

        if (withdrawals.isEmpty()) {
            return ResponseEntity.ok(CommonResponse.<List<WithdrawalResponse>>builder()
                    .statusCode(200)
                    .message("Withdrawal created successfully")
                    .data(new ArrayList<>())
                    .build());
        }

        return ResponseEntity.ok(CommonResponse.<List<WithdrawalResponse>>builder()
                .statusCode(200)
                .message("Withdrawal created successfully")
                .data(withdrawals.stream().map(w -> w.toResponse()).toList())
                .build());

    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<CommonResponse<WithdrawalResponse>> approveWithdrawal(
            @PathVariable String id) {

        Withdrawals withdrawal = withdrawalService.getById(id);

        if (withdrawal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Withdrawal not found");
        }

        withdrawal.setStatus(WithdrawalStatus.APPROVED.name());
        withdrawal = withdrawalService.update(withdrawal);

        return ResponseEntity.ok(CommonResponse.<WithdrawalResponse>builder()
                .statusCode(200)
                .message("Withdrawal approved successfully")
                .data(withdrawal.toResponse())
                .build());
    }
}