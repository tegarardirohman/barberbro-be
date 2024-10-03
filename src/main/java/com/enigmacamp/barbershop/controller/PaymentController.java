package com.enigmacamp.barbershop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enigmacamp.barbershop.constant.ResponseMessage;
import com.enigmacamp.barbershop.model.dto.request.BookingRequest;
import com.enigmacamp.barbershop.model.dto.request.PaymentRequest;
import com.enigmacamp.barbershop.model.dto.response.BookingResponse;
import com.enigmacamp.barbershop.model.dto.response.CommonResponse;
import com.enigmacamp.barbershop.model.dto.response.PaymentResponse;
import com.enigmacamp.barbershop.model.entity.Booking;
import com.enigmacamp.barbershop.model.entity.Payment;
import com.enigmacamp.barbershop.service.BookingService;
import com.enigmacamp.barbershop.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {
    private final PaymentService paymentService;
    private final BookingService bookingService;

    // @PostMapping("/bookings/{id}/payments")
    // public ResponseEntity<CommonResponse<BookingResponse>> createPayment(@RequestBody BookingRequest request,
    //         HttpServletRequest srvrequest) {

    //     Booking booking = bookingService.getById("");

    //     if (booking == null) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
    //     }

    //     booking = bookingService.getMidtransUrl(booking);

    //     if (booking == null) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
    //     }

    //     return ResponseEntity.ok(CommonResponse.<BookingResponse>builder()
    //             .statusCode(200)
    //             .message("Payment created successfully")
    //             .data(booking.toResponse())
    //             .build());

    //     // Payment paymentEntity = Payment.builder()
    //     //         .bookingId(booking)
    //     //         .paymentMethod(payment.getPaymentMethod())
    //     //         .transactionDate(System.currentTimeMillis())
    //     //         .createdAt(System.currentTimeMillis())
    //     //         .build();

    //     // paymentEntity = paymentService.create(paymentEntity, booking);

    //     // if (paymentEntity == null) {
    //     //     throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create payment");
    //     // }

    //     // return ResponseEntity.ok(CommonResponse.<PaymentResponse>builder()
    //     //         .statusCode(200)
    //     //         .message("Payment created successfully")
    //     //         .data(paymentEntity.toResponse())
    //     //         .build());

    // }

    // @GetMapping("/payments/{id}/update")
    // public ResponseEntity<CommonResponse<PaymentResponse>> updatePayment(@PathVariable String id) {

    //     Payment payment = paymentService.getById(id);

    //     if (payment == null) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
    //     }

    //     Booking booking = bookingService.getById(payment.getBookingId().getBookingId());

    //     if (booking == null) {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
    //     }

    //     payment = paymentService.updatePaymentStatus(payment, booking);

    //     return ResponseEntity.ok(CommonResponse.<PaymentResponse>builder()
    //             .statusCode(200)
    //             .message("Payment updated successfully")
    //             .data(payment.toResponse())
    //             .build());
    // }
}