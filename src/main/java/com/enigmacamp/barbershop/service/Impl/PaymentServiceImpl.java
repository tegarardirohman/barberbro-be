package com.enigmacamp.barbershop.service.Impl;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.enigmacamp.barbershop.constant.BookingStatus;
import com.enigmacamp.barbershop.constant.PaymentStatus;
import com.enigmacamp.barbershop.model.dto.request.MidtransRequest;
import com.enigmacamp.barbershop.model.entity.Barbers;
import com.enigmacamp.barbershop.model.entity.Booking;
import com.enigmacamp.barbershop.model.entity.Customer;
import com.enigmacamp.barbershop.model.entity.Payment;
import com.enigmacamp.barbershop.repository.PaymentRepository;
import com.enigmacamp.barbershop.service.BarberService;
import com.enigmacamp.barbershop.service.BookingService;
import com.enigmacamp.barbershop.service.PaymentService;

@Repository
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;
    private final RestClient restClient;
    private final BarberService barberService;
    private final String MIDTRANS_KEY;
    private final String MIDTRANS_URL;

    @Autowired
    public PaymentServiceImpl(@Value("${midtrans.api.server-key}") String secretKey,
            @Value("${midtrans.api.snap-url}") String snapUrl, RestClient restClient,
            PaymentRepository paymentRepository, BookingService bookingService, BarberService barberService) {
        this.barberService = barberService;
        this.bookingService = bookingService;
        this.paymentRepository = paymentRepository;
        this.restClient = restClient;
        this.MIDTRANS_KEY = secretKey;
        this.MIDTRANS_URL = snapUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Payment create(Payment payment, Booking booking) {
        try {
            Customer customer = booking.getCustomerId();
            List<MidtransRequest.ItemDetails> services = booking.getServices().stream()
                    .map(service -> MidtransRequest.ItemDetails.builder()
                            .id(service.getService_id())
                            .price((double) Math.round(service.getPrice()))
                            .quantity(1)
                            .name(service.getService_name())
                            .build())
                    .toList();

            Double amount = services.stream().mapToDouble(s -> s.getPrice()).sum();

            payment.setAmount(amount);
            payment.setPaymentStatus(PaymentStatus.PENDING.name());

            String base64EncodedKey = Base64.getEncoder().encodeToString((MIDTRANS_KEY + ":").getBytes());

            MidtransRequest midtransRequest = MidtransRequest.builder()
                    .transaction_details(MidtransRequest.TransactionDetails.builder()
                            .orderId(booking.getBookingId())
                            .grossAmount(amount)
                            .build())
                    .creditCard(MidtransRequest.CreditCard.builder()
                            .secure(true)
                            .build())
                    .customerDetails(MidtransRequest.CustomerDetails.builder()
                            .firstName(customer.getFirstName())
                            .lastName(customer.getSurname())
                            .email(customer.getUserId().getEmail())
                            .phone(customer.getPhone())
                            .build())
                    .itemDetails(services)
                    .build();

            ResponseEntity<Map<String, String>> response = restClient.post()
                    .uri(MIDTRANS_URL)
                    .body(midtransRequest)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + base64EncodedKey)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<Map<String, String>>() {
                    });

            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                payment.setMidtransPaymentUrl(response.getBody().get("redirect_url"));
            }

            return paymentRepository.saveAndFlush(payment);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePaymentStatusWebhook(Map<String, Object> payload) {
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Payment updatePaymentStatus(Payment payment, Booking booking) {
        try {

            if (payment.getPaymentStatus().equals(PaymentStatus.COMPLETED.name())) {
                return payment;
            }

            Barbers barber = booking.getBarberId();

            String base64EncodedKey = Base64.getEncoder().encodeToString((MIDTRANS_KEY + ":").getBytes());

            ResponseEntity<Map<String, String>> response = restClient.get()
                    .uri("https://api.sandbox.midtrans.com/v2/" + booking.getBookingId() + "/status")
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + base64EncodedKey)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<Map<String, String>>() {
                    });

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                if (response.getBody().get("transaction_status").equals("settlement")) {
                    booking.setStatus(BookingStatus.Confirmed.name());
                    payment.setPaymentStatus(PaymentStatus.COMPLETED.name());
                    barber.setBalance((float) (payment.getAmount() + barber.getBalance()));
                    barberService.update(barber);
                } else {
                    booking.setStatus(PaymentStatus.PENDING.name());
                    payment.setPaymentStatus(PaymentStatus.PENDING.name());
                }

                bookingService.update(booking);
                paymentRepository.saveAndFlush(payment);
            }

            return payment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Payment getById(String id) {
        return paymentRepository.findById(id).orElse(null);
    }
}